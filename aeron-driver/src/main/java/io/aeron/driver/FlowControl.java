/*
 * Copyright 2014 - 2016 Real Logic Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.aeron.driver;

import io.aeron.protocol.StatusMessageFlyweight;

import java.net.InetSocketAddress;

/**
 * Strategy for applying flow control to the {@link Sender}.
 */
public interface FlowControl
{
    /**
     * Update the sender flow control strategy based on a status message from the receiver.
     *
     * @param flyweight           the Status Message contents
     * @param receiverAddress     of the receiver.
     * @param senderLimit         the current sender position limit.
     * @param initialTermId       for the term buffers.
     * @param positionBitsToShift in use for the length of each term buffer.
     * @param now                 current nano clock time (in nanoseconds). {@link System#nanoTime()}
     * @return the new position limit to be employed by the sender.
     */
    long onStatusMessage(
        StatusMessageFlyweight flyweight,
        InetSocketAddress receiverAddress,
        long senderLimit,
        int initialTermId,
        int positionBitsToShift,
        long now);

    /**
     * Initialize the flow control strategy
     *
     * @param initialTermId      for the term buffers
     * @param termBufferCapacity to use as the length of each term buffer
     */
    void initialize(int initialTermId, int termBufferCapacity);

    /**
     * Perform any maintenance needed by the flow control strategy and return current position
     *
     * @param now         time in nanoseconds.
     * @param senderLimit for the current sender position.
     * @return the position limit to be employed by the sender.
     */
    long onIdle(long now, long senderLimit);
}
