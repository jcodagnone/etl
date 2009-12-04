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

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import ar.com.zauber.labs.kraken.providers.wikipedia.model.Language;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiPage;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiPageRetriever;


/**
 * tests {@link MutableWikiPageRetriever}
 * 
 * @author Juan F. Codagnone
 * @since Sep 20, 2009
 */
public class MutableWikiPageRetrieverTest {
    
    /** test */
    @Test
    public final void testname() throws Exception {
        final MutableWikiPageRetriever mutableWikiPageRetriever = 
            new MutableWikiPageRetriever();
        try {
            mutableWikiPageRetriever.get(LangUtils.EN, "foo");
        } catch(final IllegalStateException e) {
            assertEquals("must call setTarget() before using it", e.getMessage());
        }
        
        final List<Language> langs = new LinkedList<Language>();
        final List<String> titles = new LinkedList<String>();
        final WikiPageRetriever retriver = new WikiPageRetriever() {
            public WikiPage get(final Language language, final String pageTitle) {
                langs.add(language);
                titles.add(pageTitle);
                return null;
            }
        };
        mutableWikiPageRetriever.setTarget(retriver);
        mutableWikiPageRetriever.get(LangUtils.EN, "foo");
        assertEquals(Arrays.asList(LangUtils.EN), langs);
        assertEquals(Arrays.asList("foo"), titles);
        assertSame(retriver, mutableWikiPageRetriever.getTarget());
    }
}
