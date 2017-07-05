package com.waes.comparator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Document {

	/**
	 * Using the primitive type, since it does not accept null values.
	 */
	@Id
	private long id;
	
	/**
	 * Left side of the comparison
	 */
	@Lob
	@Column(length = 32000)
	private String left;
	
	/**
	 * Right side of the comparison
	 */
	@Lob
	@Column(length = 32000)
	private String right;

	/**
	 * Empty constructor for creating instances
	 */
	public Document() {

	}

	/**
	 * This constructor creates new object instances for validation and persistence.
	 * 
	 * @param id of the object. It must to be informed. It is not auto-generated.
	 * @param left side of data is being sent through the request.
	 * @param right side of data is being sent through the request.
	 */
	public Document(long id, String left, String right) {
		this.id = id;
		this.left = left;
		this.right = right;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}
	
	/**
	 * 
	 * AEclipse auto-generated hashCode() for comparing each attribute.
	 *
	 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique Santana</a>.
	 * @return
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}

	/**
	 * 
	 * Eclipse auto-generated equals() for comparing each attribute.
	 *
	 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique Santana</a>.
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Document other = (Document) obj;
		if (id != other.id)
			return false;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		return true;
	}

	/**
	 * 
	 * Formatting the toString() for presenting all class fields in a single String.
	 *
	 * @author <a href="mailto:luizhenriquesantana@gmail.com">Luiz Henrique Santana</a>.
	 * @return
	 */
	@Override
	public String toString() {
		return "Document [id=" + getId() + ", left=" + getLeft() + ", right=" + getRight() + "]";
	}
}