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

import org.junit.Assert;
import org.junit.Test;

import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiLink;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiPageRetriever;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.impl.InmutableWikiLink;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.impl.LangUtils;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.impl.NotImplementedWikiPageRetriever;


/**
 * Tests {@link ArgentinaWikiSecondLevel}
 * 
 * @author Juan F. Codagnone
 * @since Sep 21, 2009
 */
public class ArgentinaWikiSecondLevelTest {
    private final WikiPageRetriever wikiPageRetriever =  
        new NotImplementedWikiPageRetriever();
    private final WikiLink province = new InmutableWikiLink(
            "[[Provincia de Buenos Aires|Buenos Aires]]",
            wikiPageRetriever, LangUtils.EN);
    private final WikiLink department = new InmutableWikiLink(
            "[[Adolfo Alsina (partido)]]",
            wikiPageRetriever, LangUtils.EN);
    private final WikiLink capitalCity = new InmutableWikiLink("[[Carhué]]",
            wikiPageRetriever, LangUtils.EN);
    private final ArgentinaWikiSecondLevel l = new ArgentinaWikiSecondLevel(
            province, department, capitalCity);
    
    /** test {@link ArgentinaDepartmentsSync#toString()} */
    @Test
    public final void testToString() {
        
        Assert.assertEquals("{province: { href: 'Provincia de Buenos Aires', "
                + "value: 'Buenos Aires'}, department: { href: "
                + "'Adolfo Alsina (partido)', value: 'null'}, capital: { "
                + "href: 'Carhué', value: 'null'}}", l.toString());
    }
    
    /** test equals */
    @Test
    public final void testEquals() {
        Assert.assertEquals(l, l);
        Assert.assertEquals(l, new ArgentinaWikiSecondLevel(
                province, department, capitalCity));
        Assert.assertEquals(l.hashCode(), l.hashCode());
        Assert.assertEquals(province, l.getProvince());
        Assert.assertEquals(department, l.getDepartment());
        Assert.assertEquals(capitalCity, l.getCapitalCity());
    }
}
