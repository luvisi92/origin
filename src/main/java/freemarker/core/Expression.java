package freemarker.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.ext.beans.BeanModel;
import freemarker.template.Template;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateCollectionModel;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateModelIterator;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateScalarModel;
import freemarker.template.TemplateSequenceModel;

public abstract class Expression
  extends TemplateObject
{
protected static final Logger LOGGER       = LoggerFactory.getLogger(Expression.class);
  TemplateModel constantValue;
  
  abstract TemplateModel _getAsTemplateModel(Environment paramEnvironment)
    throws TemplateException;
  
  abstract boolean isLiteral();
  
  void setLocation(Template template, int beginColumn, int beginLine, int endColumn, int endLine)
    throws ParseException
  {
    super.setLocation(template, beginColumn, beginLine, endColumn, endLine);
    if (isLiteral()) {
      try
      {
        this.constantValue = _getAsTemplateModel(null);
      }
      catch (Exception e) {}
    }
  }
  
  public final TemplateModel getAsTemplateModel(Environment env)
    throws TemplateException
  {
      try{
    return this.constantValue != null ? this.constantValue : _getAsTemplateModel(env);
      }catch(Exception e)
      {
          LOGGER.debug("", e);
          return null;
      }
  }
  
  String getStringValue(Environment env)
    throws TemplateException
  {
    return getStringValue(getAsTemplateModel(env), this, env);
  }
  
  static String getStringValue(TemplateModel referentModel, Expression exp, Environment env)
    throws TemplateException
  {
    if ((referentModel instanceof TemplateNumberModel)) {
      return env.formatNumber(EvaluationUtil.getNumber((TemplateNumberModel)referentModel, exp, env));
    }
    if ((referentModel instanceof TemplateDateModel))
    {
      TemplateDateModel dm = (TemplateDateModel)referentModel;
      return env.formatDate(EvaluationUtil.getDate(dm, exp, env), dm.getDateType());
    }
    if ((referentModel instanceof TemplateScalarModel)) {
      return EvaluationUtil.getString((TemplateScalarModel)referentModel, exp, env);
    }
    if (env.isClassicCompatible())
    {
      if ((referentModel instanceof TemplateBooleanModel)) {
        return ((TemplateBooleanModel)referentModel).getAsBoolean() ? "true" : "";
      }
      if (referentModel == null) {
        return "";
      }
    }
    try{
    assertNonNull(referentModel, exp, env);
    
    String msg = "Error " + exp.getStartLocation() + "\nExpecting a string, " + (env.isClassicCompatible() ? "boolean, " : "") + "date or number here, Expression " + exp + " is instead a " + referentModel.getClass().getName();
    
    throw new NonStringException(msg, env);
    }catch(Exception e)
    {
        LOGGER.debug("", e);
    }
    return "";
  }
  
  Expression deepClone(String name, Expression subst)
  {
    Expression clone = _deepClone(name, subst);
    clone.copyLocationFrom(this);
    return clone;
  }
  
  abstract Expression _deepClone(String paramString, Expression paramExpression);
  
  boolean isTrue(Environment env)
    throws TemplateException
  {
    TemplateModel referent = getAsTemplateModel(env);
    if ((referent instanceof TemplateBooleanModel)) {
      return ((TemplateBooleanModel)referent).getAsBoolean();
    }
    if (env.isClassicCompatible()) {
      return (referent != null) && (!isEmpty(referent));
    }
     try{
    assertNonNull(referent, this, env);
    String msg = "Error " + getStartLocation() + "\nExpecting a boolean (true/false) expression here" + "\nExpression " + this + " does not evaluate to true/false " + "\nit is an instance of " + referent.getClass().getName();
    
    throw new NonBooleanException(msg, env);
  }catch(Exception e)
  {
      LOGGER.debug("", e);
  }
  return false;
  }
  
  static boolean isEmpty(TemplateModel model)
    throws TemplateModelException
  {
    if ((model instanceof BeanModel)) {
      return ((BeanModel)model).isEmpty();
    }
    if ((model instanceof TemplateSequenceModel)) {
      return ((TemplateSequenceModel)model).size() == 0;
    }
    if ((model instanceof TemplateScalarModel))
    {
      String s = ((TemplateScalarModel)model).getAsString();
      return (s == null) || (s.length() == 0);
    }
    if ((model instanceof TemplateCollectionModel)) {
      return !((TemplateCollectionModel)model).iterator().hasNext();
    }
    if ((model instanceof TemplateHashModel)) {
      return ((TemplateHashModel)model).isEmpty();
    }
    if (((model instanceof TemplateNumberModel)) || ((model instanceof TemplateDateModel)) || ((model instanceof TemplateBooleanModel))) {
      return false;
    }
      
    return true;
  }
}
