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
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ar.com.zauber.labs.kraken.providers.wikipedia.model.Language;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiPage;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiPageRetriever;


/**
 * Tests {@link LazyWikiPageRetriever}
 * 
 * 
 * @author Juan F. Codagnone
 * @since Sep 20, 2009
 */
public class LazyWikiPageRetrieverTest {

    /** test */
    @Test
    public final void testFoo() {
        final List<WikiPage> activate = new LinkedList<WikiPage>();   
        final WikiPageRetriever pr = 
            new LazyWikiPageRetriever(new WikiPageRetriever() {
            public WikiPage get(final Language language, final String pageTitle) {
                return activate.size() == 0 ? null : activate.get(0);
            }
        });
        final WikiPage page = pr.get(LangUtils.EN, "Foo");
        Assert.assertNotNull(page);
        Assert.assertEquals(LangUtils.EN, page.getLanguage());
        try {
            page.getTranslatedTitles(); 
        } catch(final AssertionError e) {
            Assert.assertEquals("target wiki page retriever returned null page", 
                    e.getMessage());
        }
        activate.add(new SimpleWikiPage(1L, LangUtils.EN, 
                new HashSet<String>(Arrays.asList("Foo")), 
                new HashMap<Language, WikiPage>()));
        page.getTranslatedTitles();
    }
}
