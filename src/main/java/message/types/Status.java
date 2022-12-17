
package message.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "angle", "sensorPosX", "sensorPosY" })
public class Status implements Serializable {

	@JsonProperty("name")
	private String name;
	@JsonProperty("angle")
	private Double angle;
	@JsonProperty("sensorPosX")
	private Double sensorPosX;
	@JsonProperty("sensorPosY")
	private Double sensorPosY;

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("angle")
	public Double getAngle() {
		return angle;
	}

	@JsonProperty("angle")
	public void setAngle(Double angle) {
		this.angle = angle;
	}

	@JsonProperty("sensorPosX")
	public Double getSensorPosX() {
		return sensorPosX;
	}

	@JsonProperty("sensorPosX")
	public void setSensorPosX(Double sensorPosX) {
		this.sensorPosX = sensorPosX;
	}

	@JsonProperty("sensorPosY")
	public Double getSensorPosY() {
		return sensorPosY;
	}

	@JsonProperty("sensorPosY")
	public void setSensorPosY(Double sensorPosY) {
		this.sensorPosY = sensorPosY;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("name", name).append("angle", angle).append("sensorPosX", sensorPosX)
				.append("sensorPosY", sensorPosY).toString();
	}

}
