package congo.api.resource;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(value = "api", collectionRelation = "api")
public class ApiGetResource extends ResourceSupport
{
}
