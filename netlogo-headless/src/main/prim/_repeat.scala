// (C) Uri Wilensky. https://github.com/NetLogo/NetLogo

package org.nlogo.prim

import org.nlogo.core.Let
import org.nlogo.nvm.{ AssemblerAssistant, Command, Context, CustomAssembled, MutableLong }

class _repeat extends Command with CustomAssembled {

  // MethodRipper won't let us call a public method from perform_1() - ST 7/20/12
  private[this] val _let = Let()
  def let = _let

  override def perform(context: Context) {
    perform_1(context, argEvalDoubleValue(context, 0))
  }

  def perform_1(context: Context, d0: Double) {
    context.let(_let, new MutableLong(validLong(d0)))
    context.ip = offset
  }

  override def toString =
    super.toString + ":+" + offset

  override def assemble(a: AssemblerAssistant) {
    a.add(this)
    a.block()
    a.resume()
    a.add(new _repeatinternal(1 - a.offset, let))
  }

}
