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

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import ar.com.zauber.labs.kraken.providers.wikipedia.model.Language;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiPage;

/**
 * Simple  Implementation for {@link WikiPage}.
 * 
 * @author Juan F. Codagnone
 * @since Sep 13, 2009
 */
public class SimpleWikiPage implements WikiPage {
    private final long pageId;
    private final Language lang;
    private final Set<String> titles;
    private final Map<Language, WikiPage> langTitles;


    /** Creates the SimpleWikiPage. */
    public SimpleWikiPage(
            final long pageId,
            final Language lang,
            final Set<String> titles,
            final Map<Language, WikiPage> langTitles) {
        
        Validate.notNull(lang, "null language");
        Validate.notNull(titles,  "null titles");
        Validate.isTrue(titles.size()  > 0, 
                "wiki page must have at least one title");
        for(final String title : titles) {
            Validate.isTrue(StringUtils.isNotBlank(title), "invalid title");
        }
        
        for(final Map.Entry<Language, WikiPage> langTitle : langTitles.entrySet()) {
            Validate.notNull(langTitle.getKey());
            Validate.notNull(langTitle.getValue());
        }
        
        this.pageId = pageId;
        this.lang = lang;
        this.titles = Collections.unmodifiableSet(titles);
        this.langTitles = Collections.unmodifiableMap(langTitles);
    }

    /** @see WikiPage#getLanguage() */
    public final Language getLanguage() {
        return lang;
    }
    
    /** @see WikiPage#getPageId() */
    public final long getPageId() {
        return pageId;
    }

    /** @see WikiPage#getTitles() */
    public final Set<String> getTitles() {
        return titles;
    }
    
    
    /** @see WikiPage#getTranslatedTitles() */
    public final Map<Language, WikiPage> getTranslatedTitles() {
        return langTitles;
    }
    
    
    /** @see Object#toString() */
    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{pageid: ");
        sb.append(pageId);
        sb.append(", lang: ");
        sb.append(lang);
        sb.append(", titles: ");
        sb.append(titles);
        sb.append(", langtitles: ");
        sb.append(langTitles);
        sb.append('}');
        return sb.toString();
    }
}
