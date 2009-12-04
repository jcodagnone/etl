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

import java.io.Writer;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiPage;
import ar.com.zauber.labs.kraken.providers.wikipedia.model.impl.LangUtils;

/**
 * Generates a TSV file for {@link ArgentinaWikiSecondLevel}.
 * 
 * @author Juan F. Codagnone
 * @since Sep 21, 2009
 */
public class TabseparatedValueArgentinaWikiSecondLevelClosure implements
        Closure<ArgentinaWikiSecondLevel> {
    private final TabSeparatedWriter departments;
    
    /** Creates the TabseparatedValueArgentinaWikiSecondLevelClosure. */
    public TabseparatedValueArgentinaWikiSecondLevelClosure(
            final Writer departments) {
        Validate.notNull(departments);
        
        this.departments = new TabSeparatedWriter(departments);
//        this.departments.write(
//                "/type/object/type",
//                "/type/object/name",
//                "/location/ar_department/capital"
//        );
    }
    private int i = 0;
    /** @see Closure#execute(Object) */
    public final void execute(final ArgentinaWikiSecondLevel t) {
        final WikiPage department = getEnglishPage(t.getDepartment().getHref());
        final WikiPage capital = getEnglishPage(t.getCapitalCity().getHref());
        final WikiPage province = getEnglishPage(t.getProvince().getHref());

        if (department != null && capital != null && province != null) {
            i++;
        } else {

            this.departments.write(
//                    "/location/ar_department",
                    Long.toString(province.getPageId()),
                    department == null ? null : Long.toString(department.getPageId()),
                    capital == null ? null : Long.toString(capital.getPageId())
//                C    t.getCapitalCity().getHref().getTitles().toArray()[0].toString()
            );
        }
    }

    /**
     * @param page
     * @return
     */
    private WikiPage getEnglishPage(final WikiPage page) {
        WikiPage ret = null;
        if (page.getLanguage().equals(LangUtils.EN)) {
            ret = page;
        } else {
            ret = page.getTranslatedTitles().get(LangUtils.EN);
        }
        return ret;
    }
}
