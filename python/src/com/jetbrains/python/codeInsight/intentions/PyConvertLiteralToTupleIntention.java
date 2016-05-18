/*
 * Copyright 2000-2015 JetBrains s.r.o.
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
package com.jetbrains.python.codeInsight.intentions;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.jetbrains.python.PyTokenTypes;
import com.jetbrains.python.psi.PyElementGenerator;
import com.jetbrains.python.psi.PyExpression;
import com.jetbrains.python.psi.PySequenceExpression;
import com.jetbrains.python.psi.PyTupleExpression;
import com.jetbrains.python.psi.impl.PyPsiUtils;
import org.jetbrains.annotations.NotNull;

/**
 * @author Mikhail Golubev
 */
public class PyConvertLiteralToTupleIntention extends PyBaseConvertCollectionLiteralIntention {
  public PyConvertLiteralToTupleIntention() {
    super(PyTupleExpression.class, "tuple", "(", ")");
  }

  @NotNull
  @Override
  protected PsiElement prepareOriginalElementCopy(@NotNull PsiElement copy) {
    final PySequenceExpression sequenceExpression = unwrapCollection(copy);
    final PyExpression[] elements = sequenceExpression.getElements();
    if (elements.length == 1) {
      final PyExpression onlyElement = elements[0];
      final PsiElement next = PyPsiUtils.getNextNonCommentSibling(onlyElement, true);
      if (next != null && next.getNode().getElementType() != PyTokenTypes.COMMA) {
        final PyElementGenerator generator = PyElementGenerator.getInstance(copy.getProject());
        final ASTNode anchor = onlyElement.getNode().getTreeNext();
        sequenceExpression.getNode().addChild(generator.createComma(), anchor);
      }
    }
    return copy;
  }
}
