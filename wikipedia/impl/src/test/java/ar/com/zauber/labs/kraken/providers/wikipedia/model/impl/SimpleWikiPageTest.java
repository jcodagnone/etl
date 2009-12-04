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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

import ar.com.zauber.labs.kraken.providers.wikipedia.model.Language;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiPage;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiPageRetriever;


/**
 * Tests {@link WikiPageRetriever}
 * 
 * @author Juan F. Codagnone
 * @since Sep 20, 2009
 */
public class SimpleWikiPageTest {

    /** test */
    @Test
    public final void noTitle() {
        try {
            new SimpleWikiPage(1L, LangUtils.EN, new HashSet<String>(), 
                    new HashMap<Language, WikiPage>());
        } catch(final IllegalArgumentException e) {
            Assert.assertEquals("wiki page must have at least one title", 
                    e.getMessage());
        }
    }
    
    /** test */
    @Test
    public final void testToString() {
        final WikiPage page = new SimpleWikiPage(1L, LangUtils.EN, 
                new HashSet<String>(Arrays.asList("Foo")), 
                new HashMap<Language, WikiPage>());
        Assert.assertEquals("{pageid: 1, lang: en, titles: [Foo], langtitles: {}}",
                page.toString());
        
        Assert.assertEquals(LangUtils.EN, page.getLanguage());
        Assert.assertEquals(1L, page.getPageId());
        Assert.assertEquals(new HashSet<String>(Arrays.asList("Foo")), 
                page.getTitles());
        Assert.assertEquals(new HashMap<Language, WikiPage>(), 
                page.getTranslatedTitles());
    }
}
