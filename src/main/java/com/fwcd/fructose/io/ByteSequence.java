package com.fwcd.fructose.io;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.BitSet;

import com.fwcd.fructose.annotation.WIP;
import com.fwcd.fructose.math.ExtMath;

/**
 * A mutable, arbitrarily large, growable byte array
 * that can be interpreted as a number or as a UTF-String. Thus it fixes
 * the gap between {@link BigInteger}, {@link ByteArrayOutputStream}
 * and various other unsigned byte handling classes.
 *
 * It is still unfinished though and thus {@link BitSet} might be a better choice for now.
 */
@WIP(usable = false)
public class ByteSequence {
	private final int capacityIncrement;
	private byte[] bigEndianData;
	private int sizeInBytes;
	
	public ByteSequence() {
		this(8, 8);
	}
	
	public ByteSequence(String utfString) {
		capacityIncrement = 8;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeUTF(utfString);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		bigEndianData = baos.toByteArray();
		sizeInBytes = bigEndianData.length;
	}
	
	public ByteSequence(long numericValue) {
		capacityIncrement = 8;
		bigEndianData = BigInteger.valueOf(numericValue).toByteArray();
		sizeInBytes = bigEndianData.length;
	}
	
	public ByteSequence(byte[] bigEndianData) {
		capacityIncrement = 8;
		this.bigEndianData = Arrays.copyOf(bigEndianData, bigEndianData.length);
		sizeInBytes = bigEndianData.length;
	}
	
	public ByteSequence(int initialCapacity, int capacityIncrement) {
		this.capacityIncrement = capacityIncrement;
		bigEndianData = new byte[initialCapacity];
		sizeInBytes = 1;
	}
	
	public OutputStream getOutputStream() {
		return new OutputStream() {
			@Override
			public void write(int b) { appendRight((byte) b); }

			@Override
			public void write(byte[] b) { appendRight(b); }
		};
	}
	
	public int sizeInBits() {
		return sizeInBytes * 8;
	}
	
	public int sizeInBytes() {
		return sizeInBytes;
	}
	
	private void ensureCapacity(int bytesDelta, boolean right) {
		if (bigEndianData.length < (sizeInBytes + bytesDelta)) {
			byte[] res = new byte[sizeInBytes + bytesDelta + capacityIncrement];
			
			if (right) {
				System.arraycopy(bigEndianData, 0, res, 0, bigEndianData.length);
			} else {
				System.arraycopy(bigEndianData, 0, res, res.length - bigEndianData.length, bigEndianData.length);
			}
			
			bigEndianData = res;
		}
	}

	private byte toUnsignedByte(int representation) {
		return (byte) (representation & 0xFF);
	}

	private int fromUnsignedByte(byte b) {
		return Byte.toUnsignedInt(b);
	}
	
	private boolean getBit(int index, boolean... bits) {
		return (index < 0 || index >= bits.length) ? false : bits[index];
	}
	
	@WIP(usable = false)
	@Deprecated // TODO: Unfinished
	public byte[] toBytes(boolean... bits) {
		byte[] bytes = new byte[ExtMath.ceilDivide(bits.length, 8)];
		int byteIndex = 0;
		
		for (int i=0; i<bits.length; i+=8) {
			int byteInt = 0;
			for (int j=0; j<8; j++) {
				byteInt = (byteInt << 1) | (getBit(j + i, bits) ? 1 : 0);
			}
			bytes[byteIndex++] = toUnsignedByte(byteInt);
		}
		
		return bytes;
	}
	
	@WIP(usable = false)
	@Deprecated // TODO: Unfinished
	public void appendRight(boolean... bits) {
		appendRight(toBytes(bits));
	}
	
	public void appendRight(byte b) {
		ensureCapacity(1, true);
		bigEndianData[sizeInBytes] = b;
		sizeInBytes++;
	}
	
	public void appendRight(byte... arr) {
		ensureCapacity(arr.length, true);
		System.arraycopy(arr, 0, bigEndianData, sizeInBytes, arr.length);
		sizeInBytes += arr.length;
	}
	
	public boolean getBitFromLeft(int index) {
		int byteInNum = index / 8;
		int bitFromRightInByte = 7 - (index % 8);
		
		return ((bigEndianData[byteInNum] >>> bitFromRightInByte) & 0b1) != 0;
	}
	
	public byte getByte(int indexFromLeft) {
		return bigEndianData[indexFromLeft];
	}
	
	public String toUTFString() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(sizeInBytes);
		try {
			baos.write(bigEndianData);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		return baos.toString();
	}
	
	public int toInt() {
		return (255 * (sizeInBytes - 1)) + fromUnsignedByte(bigEndianData[bigEndianData.length - sizeInBytes]);
	}
	
	public long toLong() {
		return (255L * (sizeInBytes - 1)) + fromUnsignedByte(bigEndianData[bigEndianData.length - sizeInBytes]);
	}
	
	public BigInteger toBigInt() {
		BigInteger result = BigInteger.ZERO;
		boolean firstLoop = true;
		
		for (byte b : bigEndianData) {
			if (firstLoop) {
				firstLoop = false;
			} else {
				result = result.shiftLeft(8);
			}
			
			result = result.or(BigInteger.valueOf(fromUnsignedByte(b)));
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		return toBigInt().toString();
	}
	
	public String toBitString() {
		int bits = sizeInBits();
		StringBuilder str = new StringBuilder();
		boolean hasStarted = false;
		
		for (int i=0; i<bits; i++) {
			if (getBitFromLeft(i)) {
				str.append('1');
				hasStarted = true;
			} else if (hasStarted) {
				str.append('0');
			}
		}
		
		return str.toString();
	}
	
	public String toBytesString() {
		StringBuilder s = new StringBuilder("[");
		for (byte b : bigEndianData) {
			s.append(fromUnsignedByte(b)).append(", ");
		}
		return s.delete(s.length() - 2, s.length()).append("]").toString();
	}

	public byte[] getByteArray() {
		return bigEndianData;
	}
}
