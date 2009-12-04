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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiLink;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiPage;

/**
 * Tests {@link InmutableWikiLink}.
 * 
 * 
 * @author Juan F. Codagnone
 * @since Sep 20, 2009
 */
public class InmutableWikiLinkTest {

    /** test */
    @Test
    public final void testOk() {
        final WikiLink wikiLink = new InmutableWikiLink(
                "[[Provincia de Buenos Aires|Buenos Aires]]", 
                new LazyWikiPageRetriever(new NotImplementedWikiPageRetriever()),
                LangUtils.EN);
        assertEquals("Buenos Aires", wikiLink.getValue());
        final WikiPage page = wikiLink.getHref();
        assertEquals(LangUtils.EN, page.getLanguage());
        assertEquals("{pageid: TBL!, lang: en, titles: TBL!, langtitles: "
                + "[Provincia de Buenos Aires]}", page.toString());
        assertEquals("{ href: 'Provincia de Buenos Aires', value: 'Buenos Aires'}", 
                wikiLink.toString());
    }
    
    /** test */
    @Test
    public final void testBad() {
        try {
            new InmutableWikiLink("Laralara", 
                    new LazyWikiPageRetriever(new NotImplementedWikiPageRetriever()),
                    LangUtils.EN);
        } catch(IllegalArgumentException e) {
            assertEquals("Illegal link: Laralara", e.getMessage());
        }
    }
    
    /** test */
    @Test
    public final void testEquals() {
        final WikiLink a = new InmutableWikiLink("[[A|B]]", 
                new NotImplementedWikiPageRetriever(), LangUtils.EN);
        final WikiLink b = new InmutableWikiLink("[[A|B]]", 
                new NotImplementedWikiPageRetriever(), LangUtils.EN);
        final WikiLink c = new InmutableWikiLink("[[A|B]]", 
                new NotImplementedWikiPageRetriever(), LangUtils.ES);
        assertEquals(a, a);
        assertEquals(a, b);
        assertFalse(a.equals(c));
        assertEquals(a.hashCode(), b.hashCode());
        
    }
}
