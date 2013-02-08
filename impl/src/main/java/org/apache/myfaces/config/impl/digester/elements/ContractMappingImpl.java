/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.myfaces.config.impl.digester.elements;

import org.apache.myfaces.config.element.ContractMapping;

/**
 *
 * @author lu4242
 */
public class ContractMappingImpl extends ContractMapping
{
    private String urlPattern;
    private String contracts;

    /**
     * @return the urlPattern
     */
    public String getUrlPattern()
    {
        return urlPattern;
    }

    /**
     * @param urlPattern the urlPattern to set
     */
    public void setUrlPattern(String urlPattern)
    {
        this.urlPattern = urlPattern;
    }

    /**
     * @return the contracts
     */
    public String getContracts()
    {
        return contracts;
    }

    /**
     * @param contracts the contracts to set
     */
    public void setContracts(String contracts)
    {
        this.contracts = contracts;
    }
    
}
