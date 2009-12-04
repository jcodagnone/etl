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

import java.util.Formatter;

import org.apache.commons.lang.Validate;

import ar.com.zauber.labs.kraken.providers.wikipedia.model.WikiLink;

/**
 * Representa una entrada conceptual de la wiki sobre que es la información
 * del segundo nivel administrativo de argentina.
 *  
 * @author Juan F. Codagnone
 * @since Sep 20, 2009
 */
public class ArgentinaWikiSecondLevel {
    private final WikiLink province;
    private final WikiLink department;
    private final WikiLink capitalCity;

    /** Creates the ArgentinaWikiSecondLevel. */
    public ArgentinaWikiSecondLevel(final WikiLink province,
            final WikiLink department, final WikiLink capitalCity) {
        Validate.notNull(province, "province is null");
        Validate.notNull(department, "department is null");
        Validate.notNull(capitalCity, "capital city is null");

        this.province = province;
        this.department = department;
        this.capitalCity = capitalCity;
    }
    
    public final WikiLink getProvince() {
        return province;
    }

    public final WikiLink getDepartment() {
        return department;
    }

    public final WikiLink getCapitalCity() {
        return capitalCity;
    }
    
    /** @see Object#toString() */
    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder();
        new Formatter(sb).format("{province: %s, department: %s, capital: %s}", 
                province, department, capitalCity);
        return sb.toString();
    }
    
    /** @see Object#equals(Object) */
    @Override
    public final boolean equals(final Object obj) {
        boolean ret = false;
        
        if(obj == this) {
            ret  = true;
        } else if(obj instanceof ArgentinaWikiSecondLevel) {
            final ArgentinaWikiSecondLevel a = (ArgentinaWikiSecondLevel) obj;
            ret = capitalCity.equals(a.capitalCity)  
               && department.equals(a.department)
               && province.equals(a.province);
        }
        
        return ret;
    }
    
    /** @see Object#hashCode() */
    @Override
    public final int hashCode() {
        int ret = 17;
        
        ret = 19 * ret + capitalCity.hashCode();
        ret = 19 * ret + department.hashCode();
        ret = 19 * ret + province.hashCode();
        
        return ret;
    }
}
