package congo.api.resource;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(value = "congo:api", collectionRelation = "congo:api")
public class ApiResource extends ResourceSupport
{
}
