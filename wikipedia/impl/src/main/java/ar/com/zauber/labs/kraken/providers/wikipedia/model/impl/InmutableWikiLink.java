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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import ar.com.zauber.labs.kraken.providers.wikipedia.model.Language;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiLink;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiPage;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiPageRetriever;

/** 
 * link a otra página  
 * 
 * @author Juan F. Codagnone
 * @since Sep 8, 2009
 */
public class InmutableWikiLink implements WikiLink {
    private final Pattern regex = Pattern.compile("^\\[\\[([^|]+)(|.+)\\]\\]$");
    private final String href;
    private final String value;
    private final WikiPageRetriever wikiPageRetriever;
    private final Language wikiLang;
    
    /**
     * Recibe la representacion en texto de la wikipedia y los separa 
     * en topico y anchor.
     * 
     *  Ej:
     *    [[Almirante Brown (partido)]] 
     *    [[Provincia de Santiago del Estero|Santiago del Estero]]
     *
     */
    public InmutableWikiLink(final String textRepresentation,
            final WikiPageRetriever wikiPageRetriever,
            final Language wikiLang) {
        Validate.isTrue(StringUtils.isNotEmpty(textRepresentation),
                "Can't create a WikiLink from null");
        Validate.notNull(wikiPageRetriever);
        Validate.notNull(wikiLang);
        
        this.wikiPageRetriever = wikiPageRetriever;
        this.wikiLang = wikiLang;
        
        final Matcher m = regex.matcher(textRepresentation);
        if(m.matches()) {
            this.href = m.group(1).trim();
            String tmp = m.group(2).trim();
            this.value = StringUtils.isBlank(tmp) ? null : tmp.substring(1);
        } else {
            throw new IllegalArgumentException("Illegal link: " 
                    + textRepresentation);
        }
    }

    public final WikiPage getHref() {
        return wikiPageRetriever.get(wikiLang, href);
    }

    public final String getValue() {
        return value;
    }

    /** @see Object#toString() */
    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{ href: '");
        sb.append(href);
        sb.append("', value: '");
        sb.append(value);
        sb.append("'}");
        
        return sb.toString();
    }
    
    /** @see Object#equals(Object) */
    @Override
    public final boolean equals(final Object obj) {
        boolean ret = false;
        
        if(obj == this) {
            ret = true;
        } else if(obj instanceof InmutableWikiLink) {
            final InmutableWikiLink link = (InmutableWikiLink) obj;
            ret = href.equals(link.href);
            ret &= wikiLang.equals(link.wikiLang);
            if(value != null) {
                ret &= value.equals(link.value);
            } else if(link.value != null) {
                ret &= link.value.equals(value);
            }
        }
        
        return ret;
    }
    
    /** @see Object#hashCode() */
    @Override
    public final int hashCode() {
        int ret = 17;
        
        ret = 19 * ret + href.hashCode();
        ret = 19 * ret + wikiLang.hashCode();
        if(value != null) {
            ret = 19 * ret + value.hashCode();    
        }
        
        return ret;
    }
}
