package org.mule.runtime.extension.api.runtime.transformer;

import org.mule.runtime.api.meta.NamedObject;
import org.mule.runtime.api.metadata.DataType;

import java.util.List;

public interface ImplicitTransformer extends NamedObject {

  List<DataType> getSourceTypes();

  DataType getOutputType();

  Object transform(Object value);
}
