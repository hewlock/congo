package congo;

import org.springframework.hateoas.ResourceSupport;

public interface DomainAssembler<R extends ResourceSupport, D>
{
	D fromResource(R resource);
}
