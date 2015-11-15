package congo;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmbeddedResourceSupport extends ResourceSupport
{
	private final Map<String, Collection<EmbeddedResourceSupport>> embedded = new HashMap<String, Collection<EmbeddedResourceSupport>>();


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("_counts")
	public Map<String, Integer> getCounts()
	{
		Map<String, Integer> counts = new HashMap<String, Integer>();
		for (Map.Entry<String, Collection<EmbeddedResourceSupport>> entry : embedded.entrySet())
		{
			counts.put(entry.getKey(), entry.getValue().size());
		}
		return counts;
	}


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("_embedded")
	public Map<String, Collection<EmbeddedResourceSupport>> getEmbedded()
	{
		return embedded;
	}


	@JsonIgnore
	public String getRelation()
	{
		return this.getClass().getAnnotation(Relation.class).value();
	}


	public void embed(EmbeddedResourceSupport resource)
	{
		embed(resource.getRelation(), resource);
		embed(resource.embedded);
		resource.embedded.clear();
	}


	public void embed(String relation, EmbeddedResourceSupport resource)
	{
		Collection<EmbeddedResourceSupport> embeds = embedded.get(relation);
		if (null == embeds)
		{
			embeds = new HashSet<EmbeddedResourceSupport>();
			embedded.put(relation, embeds);
		}
		embeds.add(resource);
	}


	public void embed(Collection<EmbeddedResourceSupport> resources)
	{
		for (EmbeddedResourceSupport resource : resources)
		{
			embed(resource);
		}
	}


	public void embed(Map<String, Collection<EmbeddedResourceSupport>> embedded)
	{
		for (Map.Entry<String, Collection<EmbeddedResourceSupport>> entry : embedded.entrySet())
		{
			String relation = entry.getKey();
			embed(relation, entry.getValue());
		}
	}


	public void embed(String relation, Collection<EmbeddedResourceSupport> resources)
	{
		for (EmbeddedResourceSupport resource : resources)
		{
			embed(relation, resource);
		}
	}
}
