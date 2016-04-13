// Copyright 2016 Twitter. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.twitter.heron.common.network;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;

/**
 * RID is a unique id to identify Request; and all message not requiring request will have
 * the zeroREDIQ as their RID.
 * The RID is generated by calling random()
 */

public class REQID {
  public static final int REQIDSize = 32;
  private byte[] bytes;
  private static Random randomGenerator = new Random(System.nanoTime());
  public static REQID zeroREQID = generateZero();

  public REQID(byte[] _bytes) {
    assert _bytes.length == REQIDSize;
    bytes = new byte[REQIDSize];
    System.arraycopy(_bytes, 0, bytes, 0, _bytes.length);
  }

  public REQID(ByteBuffer buffer) {
    bytes = new byte[REQID.REQIDSize];
    buffer.get(bytes);
  }

  public void pack(ByteBuffer buffer) {
    buffer.put(bytes);
  }

  public byte[] value() {
    return bytes;
  }

  public static REQID generate() {
    byte[] _bytes = new byte[REQIDSize];
    randomGenerator.nextBytes(_bytes);
    return new REQID(_bytes);
  }

  private static REQID generateZero() {
    byte[] _bytes = new byte[REQIDSize];
    Arrays.fill(_bytes, (byte) 0);
    return new REQID(_bytes);
  }

  public boolean equals(Object other) {
    if (other == null)
      return false;
    if (other == this)
      return true;
    if (!(other instanceof REQID))
      return false;
    REQID rother = (REQID) (other);
    return Arrays.equals(this.bytes, rother.value());
  }

  public int hashCode() {
    return Arrays.hashCode(this.bytes);
  }

  @Override
  public String toString() {
    StringBuilder bldr = new StringBuilder();
    for (int j = 0; j < bytes.length; j++) {
      bldr.append(String.format("%02X ", bytes[j]));
    }
    return bldr.toString();
  }
}
