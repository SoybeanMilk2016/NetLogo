// (C) Uri Wilensky. https://github.com/NetLogo/NetLogo

package org.nlogo.prim

import org.nlogo.agent.{ Agent, AgentSet }
import org.nlogo.api.{ AgentException, LogoException, LogoListBuilder}
import org.nlogo.core.Syntax
import org.nlogo.core.I18N
import org.nlogo.nvm.{ ArgumentTypeException, Context, Reporter }
import org.nlogo.nvm.RuntimePrimitiveException

class _linkbreedvariableof(name: String) extends Reporter {


  override def toString: String = s"${super.toString}:$name"

  override def report(context: Context): AnyRef = args(0).report(context) match {
    case agent: Agent =>
      if (agent.id == -1)
        throw new RuntimePrimitiveException(context, this,
          I18N.errors.getN("org.nlogo.$common.thatAgentIsDead", agent.classDisplayName))
      try {
        agent.getLinkBreedVariable(name)
      } catch {
        case ex: AgentException => throw new RuntimePrimitiveException(context, this, ex.getMessage)
      }
    case sourceSet: AgentSet =>
      val result = new LogoListBuilder
      try {
        val itr = sourceSet.shufflerator(context.job.random)
        while (itr.hasNext)
          result.add(itr.next().getLinkBreedVariable(name))
      } catch {
        case ex: AgentException => throw new RuntimePrimitiveException(context, this, ex.getMessage)
      }
      result.toLogoList
    case agentOrSet =>
      throw new ArgumentTypeException(context, this, 0,
        Syntax.LinksetType | Syntax.LinkType, agentOrSet)
  }
}
