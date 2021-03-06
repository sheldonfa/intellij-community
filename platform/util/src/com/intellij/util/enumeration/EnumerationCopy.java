/*
 * Copyright 2000-2013 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.util.enumeration;

import java.util.Enumeration;
import java.util.Vector;

public class EnumerationCopy implements Enumeration{
  private final Vector myElements;
  private Enumeration myEnumeration;

  public EnumerationCopy(Enumeration enumeration){
    myElements = new Vector();
    while(enumeration.hasMoreElements()){
      myElements.add(enumeration.nextElement());
    }
    reset();
  }

  public void reset(){
    myEnumeration = myElements.elements();
  }

  @Override
  public boolean hasMoreElements(){
    return myEnumeration.hasMoreElements();
  }

  @Override
  public Object nextElement(){
    return myEnumeration.nextElement();
  }

  public int getElementCount(){
    return myElements.size();
  }
}
