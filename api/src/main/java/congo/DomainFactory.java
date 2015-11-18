package congo;

import org.springframework.hateoas.ResourceSupport;

public interface DomainFactory<R extends ResourceSupport, D>
{
	D fromResource(R resource);
}
