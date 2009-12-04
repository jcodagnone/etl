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
package ar.com.zauber.labs.kraken.providers.wikipedia.schema;

import static org.junit.Assert.assertEquals;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;
import org.xml.sax.SAXException;

import ar.com.zauber.labs.semantic.administrative.wikipedia.Api;
import ar.com.zauber.labs.semantic.administrative.wikipedia.Page;

/**
 * Unmarshaller
 * 
 * @author Juan F. Codagnone
 * @since Sep 21, 2009
 */
public class SchemaTest {

    /** test */ 
    @Test
    public final void testUnmarshall() throws SAXException, JAXBException  {
        final Unmarshaller u = new WikipediaSchemaProvider().getUnmarshaller();
        final Api api = (Api) u.unmarshal(getClass().getResourceAsStream(
                "wikibot.xml"));
        final Page p = api.getQuery().getPages().getPage().get(0);
        assertEquals(16363, p.getPageid().longValue());
        assertEquals("16363|eo", 
                api.getQueryContinue().getLanglinks().getLlcontinue());
    }
}
 