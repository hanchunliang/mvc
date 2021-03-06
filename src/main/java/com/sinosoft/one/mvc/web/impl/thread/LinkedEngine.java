/*
 * Copyright 2007-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sinosoft.one.mvc.web.impl.thread;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sinosoft.one.mvc.web.impl.mapping.MappingNode;

/**
 * 
 *
 * 
 */
public final class LinkedEngine implements Engine {

    protected final Log logger = LogFactory.getLog(getClass());

    private LinkedEngine parent;

    private Engine target;

    private MappingNode node;

    public LinkedEngine(LinkedEngine parent, Engine target, MappingNode node) {
        this.parent = parent;
        this.target = target;
        this.node = node;
    }

    public MappingNode getNode() {
        return node;
    }

    public LinkedEngine getParent() {
        return parent;
    }

    public Engine getTarget() {
        return target;
    }

    public int isAccepted(HttpServletRequest request) {
        return target.isAccepted(request);
    }

    public Object execute(Mvc mvc) throws Throwable {
        if (Thread.currentThread().isInterrupted()) {
            logger.info("stop to call the next engine: thread is interrupted");
            return null;
        }
        return target.execute(mvc);
    }

    public void destroy() {
        target.destroy();
    }

}
