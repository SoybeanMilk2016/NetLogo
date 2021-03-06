// (C) Uri Wilensky. https://github.com/NetLogo/NetLogo

package org.nlogo.prim.etc

import org.nlogo.core.Syntax
import org.nlogo.nvm.{ Context, Reporter }

class _dump1 extends Reporter {

  override def report(context: Context) =
    context.activation.procedure.dump
}
