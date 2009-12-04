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

import java.io.OutputStreamWriter;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiLink;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiPageRetriever;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.impl.InmutableWikiLink;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.impl.LangUtils;


/**
 * Test {@link TabseparatedValueArgentinaWikiSecondLevelClosure}
 * 
 * 
 * @author Juan F. Codagnone
 * @since Sep 21, 2009
 */
public class TabseparatedValueArgentinaWikiSecondLevelClosureTest {
    private final WikiPageRetriever wikiPageRetriever =  
        (WikiPageRetriever) new ClassPathXmlApplicationContext(
            "ar/com/zauber/labs/kraken/providers/wikipedia/apps/administrative/"
          + "config/context-administrative-kraken-spring.xml")
        .getBean("fixedWikiPageRetriever");
    private final WikiLink province = new InmutableWikiLink(
            "[[Provincia de Buenos Aires|Buenos Aires]]",
            wikiPageRetriever, LangUtils.ES);
    private final WikiLink department = new InmutableWikiLink(
            "[[Adolfo Alsina (partido)]]",
            wikiPageRetriever, LangUtils.ES);
    private final WikiLink capitalCity = new InmutableWikiLink("[[Carhué]]",
            wikiPageRetriever, LangUtils.ES);
    private final ArgentinaWikiSecondLevel l = new ArgentinaWikiSecondLevel(
            province, department, capitalCity);

    
    /** test */
    @Test
    public final void testFoo() throws Exception {
        new TabseparatedValueArgentinaWikiSecondLevelClosure(
                new OutputStreamWriter(System.out)).execute(l);
    }
}
