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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import ar.com.zauber.commons.web.rest.impl.FixedContentProvider;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiPage;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiPageRetriever;
import ar.com.zauber.labs.kraken.providers.wikipedia.schema.WikipediaSchemaProvider;

/**
 * Tests {@link HttpWikiPageRetriever}.
 * 
 * @author Juan F. Codagnone
 * @since Sep 20, 2009
 */
public class HttpWikiPageRetrieverTest {

    /** page */ 
    @Test
    public final void testFoo() throws SAXException, JAXBException, 
                                       URISyntaxException {
        final Map<URI, String> map = new HashMap<URI, String>();
        final String baseURL = "http://en.wikipedia.org/w/api.php?action=query&"
            + "redirects=true&format=xml&titles=X&prop=langlinks";
        final String base = 
            "ar/com/zauber/labs/kraken/providers/wikipedia/model/impl/"; 
        
        map.put(new URI(baseURL), base + "buenosaires-en.xml");
        
        for(final String llc : Arrays.asList("eo", "it",  "pl", "vo")) {
            map.put(new URI(baseURL + "&llcontinue=16363%7C" + llc),
                    base + "buenosaires-en-" + llc + ".xml");    
        }
        
        final WikiPageRetriever pageRetriever = new HttpWikiPageRetriever(
                new FixedContentProvider(map), 
                new WikipediaSchemaProvider().getUnmarshaller(),
                new LazyWikiPageRetriever(new NotImplementedWikiPageRetriever()));
        
        WikiPage page = pageRetriever.get(LangUtils.EN, "X");
        Assert.assertEquals(16363L, page.getPageId());
        Assert.assertEquals(new HashSet<String>(Arrays.asList(
                "Provincia de Buenos Aires")), page.getTitles());
        Assert.assertEquals(38, page.getTranslatedTitles().size());
    }
    
    /** test */
    @Test
    public final void testWithoutLangLinks()  throws SAXException, JAXBException, 
        URISyntaxException {
        final Map<URI, String> map = new HashMap<URI, String>();
        map.put(new URI("http://es.wikipedia.org/w/api.php?action=query"
                + "&redirects=true&format=xml&titles=Tamberías"
                + "&prop=langlinks"), 
                "ar/com/zauber/labs/kraken/providers/wikipedia/model/impl/"
                + "tamberias-es.xml");
        final WikiPageRetriever pageRetriever = new HttpWikiPageRetriever(
                new FixedContentProvider(map), 
                new WikipediaSchemaProvider().getUnmarshaller(),
                new LazyWikiPageRetriever(new NotImplementedWikiPageRetriever()));
        final WikiPage wikiPage = pageRetriever.get(LangUtils.ES, "Tamberías");
        Assert.assertEquals(1130057, wikiPage.getPageId());
        
    }
}
