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
package ar.com.zauber.labs.kraken.providers.wikipedia.apps.administrative;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Assert;
import org.junit.Test;

import ar.com.zauber.commons.dao.closure.ListClosure;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiLink;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiPageRetriever;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.impl.InmutableWikiLink;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.impl.LangUtils;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.impl.NotImplementedWikiPageRetriever;


/**
 * Test  {@link ArgentinaDepartmentsSync}
 * 
 * @author Juan F. Codagnone
 * @since Sep 21, 2009
 */
public class ArgentinaDepartmentsSyncTest {

    /** test */
    @Test
    public final void testname() throws  IOException {
        final ListClosure<ArgentinaWikiSecondLevel> closure = 
            new ListClosure<ArgentinaWikiSecondLevel>();
        new ArgentinaDepartmentsSync(new BufferedReader(new InputStreamReader(
            ArgentinaDepartmentsSyncTest.class.getResourceAsStream("es-test.txt"),
                    "utf-8")), new NotImplementedWikiPageRetriever(),
                    LangUtils.ES, closure);
        Assert.assertEquals(1, closure.getList().size());
        
        final WikiPageRetriever wikiPageRetriever =  
            new NotImplementedWikiPageRetriever();
        final WikiLink province = new InmutableWikiLink(
                "[[Provincia de Buenos Aires|Buenos Aires]]",
                wikiPageRetriever, LangUtils.ES);
        final WikiLink department = new InmutableWikiLink(
                "[[Adolfo Alsina (partido)]]",
                wikiPageRetriever, LangUtils.ES);
        final WikiLink capitalCity = new InmutableWikiLink("[[Carhué]]",
                wikiPageRetriever, LangUtils.ES);
        final ArgentinaWikiSecondLevel secondLevel = 
                new ArgentinaWikiSecondLevel(province, department, 
                        capitalCity);
        
        Assert.assertEquals(secondLevel, closure.getList().get(0));
    }
}
