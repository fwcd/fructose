package fwcd.fructose;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class LensTest {
	@Test
	public void testLens() {
		Lens<House, Integer> houseArea = new Lens<>(House::getArea, House::withArea);
		Lens<House, Door> houseDoor = new Lens<>(House::getDoor, House::withDoor);
		Lens<Door, Integer> doorWidth = new Lens<>(Door::getWidth, Door::withWidth);
		Lens<Door, Integer> doorHeight = new Lens<>(Door::getHeight, Door::withHeight);
		Lens<House, Integer> houseDoorWidth = houseDoor.compose(doorWidth);
		Lens<House, Integer> houseDoorHeight = doorHeight.below(houseDoor);
		
		House myHouse = new House(new Door(4, 3), 50);
		Door myDoor = myHouse.getDoor();
		
		assertThat(houseArea.get(myHouse), equalTo(50));
		assertThat(doorWidth.set(myDoor, 10), equalTo(new Door(10, 3)));
		assertThat(doorHeight.get(myDoor), equalTo(3));
		assertThat(houseDoorWidth.get(myHouse), equalTo(4));
		assertThat(houseDoorHeight.set(myHouse, 20), equalTo(new House(new Door(4, 20), 50)));
	}
	
	// Deeply immutable structures for testing
	
	private class Door {
		private final int width;
		private final int height;
		
		public Door(int width, int height) {
			this.width = width;
			this.height = height;
		}
		
		public int getWidth() { return width; }
		
		public int getHeight() { return height; }
		
		public Door withWidth(int newWidth) { return new Door(newWidth, height); }
		
		public Door withHeight(int newHeight) { return new Door(width, newHeight); }
		
		@Override
		public boolean equals(Object obj) {
			if (obj == null) return false;
			if (this == obj) return true;
			if (!getClass().equals(obj.getClass())) return false;
			Door other = (Door) obj;
			return width == other.width && height == other.height;
		}
		
		@Override
		public int hashCode() { return 7 * width * height; }
		
		@Override
		public String toString() { return "Door(width=" + width + ", height=" + height + ")"; }
	}
	
	private class House {
		private final Door door;
		private final int area;
		
		public House(Door door, int area) {
			this.door = door;
			this.area = area;
		}
		
		public Door getDoor() { return door; }
		
		public int getArea() { return area; }
		
		public House withDoor(Door newDoor) { return new House(newDoor, area); }
		
		public House withArea(int newArea) { return new House(door, newArea); }
		
		@Override
		public boolean equals(Object obj) {
			if (obj == null) return false;
			if (this == obj) return true;
			if (!getClass().equals(obj.getClass())) return false;
			House other = (House) obj;
			return door.equals(other.door) && area == other.area;
		}
		
		@Override
		public int hashCode() { return 7 * door.hashCode() * area; }
		
		@Override
		public String toString() { return "House(door=" + door + ", area=" + area + ")"; }
	}
}
