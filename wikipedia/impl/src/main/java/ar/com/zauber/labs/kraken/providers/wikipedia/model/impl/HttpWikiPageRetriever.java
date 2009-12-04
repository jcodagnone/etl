/**
 * Copyright (c) 2009 Zauber S.A. <http://www.zauber.com.ar/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ar.com.zauber.labs.kraken.providers.wikipedia.model.impl;



import java.io.Reader;
import java.net.URI;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;
import org.springframework.web.util.UriTemplate;

import ar.com.zauber.commons.dao.exception.NoSuchEntityException;
import ar.com.zauber.commons.web.rest.ContentProvider;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.Language;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiPage;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiPageRetriever;
import ar.com.zauber.labs.semantic.administrative.wikipedia.Api;
import ar.com.zauber.labs.semantic.administrative.wikipedia.Ll;
import ar.com.zauber.labs.semantic.administrative.wikipedia.Page;
import ar.com.zauber.labs.semantic.administrative.wikipedia.Query;
import ar.com.zauber.labs.semantic.administrative.wikipedia.R;

/**
 * {@link WikiPageRetriever} that use wikipedia's API BOT to retrieve
 * metadata about wiki pages.
 * 
 * @author Juan F. Codagnone
 * @since Sep 8, 2009
 */
public class HttpWikiPageRetriever implements  WikiPageRetriever {
    private final ContentProvider contentProvider;
    private final Unmarshaller unmarshaller;
    private final WikiPageRetriever lazyWikiPageRetriever;
    
    /** 
     * @param contentProvider usado para obtener el contenido de las paginas remotas
     * @param unmarshaller usado para leer el contenido de la paginas remotas
     */
    public HttpWikiPageRetriever(final ContentProvider contentProvider,
            final Unmarshaller unmarshaller,
            final WikiPageRetriever lazyWikiPageRetriever) {
        Validate.notNull(contentProvider);
        Validate.notNull(unmarshaller);
        Validate.notNull(lazyWikiPageRetriever);
        
        this.contentProvider = contentProvider;
        this.unmarshaller = unmarshaller;
        this.lazyWikiPageRetriever = lazyWikiPageRetriever;
    }
    
    /** @see WikiPageRetriever#get(String) */
    public final WikiPage get(final Language wikipediaLang, final String pageTitle) {
        Validate.isTrue(StringUtils.isNotBlank(pageTitle), "pageTitle is blank");
        Validate.notNull(wikipediaLang, "wikipediaLang is null");
        final UriTemplate uriTemplate;

        uriTemplate = new UriTemplate(
                "http://" + wikipediaLang.getIsoName()
                + ".wikipedia.org/w/api.php?action=query&redirects=true&format=xml"
                + "&titles={title}&prop=langlinks{llcontinue}");
        
        Reader reader = null;
        try {
            String llcontinue = null;
            final Set<String> titles = new TreeSet<String>();
            final Map<Language, WikiPage> langLink = 
                new TreeMap<Language, WikiPage>();
            long pageid = 0;
            do {
                final URI url = uriTemplate.expand(pageTitle, 
                        llcontinue  == null ? "" : "&llcontinue=" + llcontinue);
                try {
                    reader = contentProvider.getContent(url);
                    final Api api = (Api) unmarshaller.unmarshal(reader);
                    IOUtils.closeQuietly(reader);
                    reader = null;
                    
                    final Query query = api.getQuery();
                    
                    if(query.getRedirects() != null) {
                        for(final R r : query.getRedirects().getR()) {
                            titles.add(r.getFrom());
                            titles.add(r.getTo());
                        }
                    }
                    final List<Page> pages = query.getPages().getPage();
                    if(pages.size() == 1) {
                        final Page page = pages.get(0);
                        pageid = page.getPageid().longValue();
                        titles.add(page.getTitle());
                        if(page.getLanglinks() != null) {
                            for(final Ll ll : page.getLanglinks().getLl()) {
                                try {
                                    final Language lang = LangUtils.getLang(
                                            ll.getLang());
                                    langLink.put(lang, 
                                            lazyWikiPageRetriever.get(lang, 
                                                    ll.getValue()));
                                } catch(final NoSuchEntityException e) {
                                    final StringBuilder sb = new StringBuilder();
                                    new Formatter(sb).format(
                                     "Unknown language %s on wiki page `%s' (%s):"
                                            + " %s",
                                      e.getEntity(), pageTitle, wikipediaLang,
                                      url);
                                    throw new IllegalArgumentException(
                                            sb.toString(), e);
                                }
                            }
                        }
                        if(api.getQueryContinue() == null) {
                            llcontinue = null;
                        } else {
                            llcontinue  = api.getQueryContinue().getLanglinks()
                                             .getLlcontinue();
                        }
                    } else {
                        throw new AssertionError("1 page expected. got " 
                                +  pages.size() 
                                + "unable to parse page " 
                                + pageTitle 
                                + " (" + url  + ")");
                    }
                } catch(final NoSuchEntityException e) {
                    throw new NoSuchEntityException(pageTitle + " (" 
                            + wikipediaLang + ")", e);
                } catch (final JAXBException e) {
                    final StringBuilder sb = new StringBuilder();
                    new Formatter(sb).format("parsing wiki page `%s' (%s): %s\n%s", 
                            pageTitle,
                            wikipediaLang,
                            url, e);
                    throw new UnhandledException(sb.toString(), e);
                }
            } while(llcontinue != null);
            
            return new SimpleWikiPage(pageid, wikipediaLang, titles, langLink);
        } finally {
            if(reader != null) {
                IOUtils.closeQuietly(reader);
            }
        }
    }
}
