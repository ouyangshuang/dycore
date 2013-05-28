/**
 * Copyright (C) 2006-2009 Dustin Sallings
 * Copyright (C) 2009-2011 Couchbase, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALING
 * IN THE SOFTWARE.
 */

package com.dooioo.commons.memcache.spy.protocol.binary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.security.auth.callback.CallbackHandler;

import com.dooioo.commons.memcache.spy.ops.BaseOperationFactory;
import com.dooioo.commons.memcache.spy.ops.CASOperation;
import com.dooioo.commons.memcache.spy.ops.ConcatenationOperation;
import com.dooioo.commons.memcache.spy.ops.ConcatenationType;
import com.dooioo.commons.memcache.spy.ops.DeleteOperation;
import com.dooioo.commons.memcache.spy.ops.FlushOperation;
import com.dooioo.commons.memcache.spy.ops.GetAndTouchOperation;
import com.dooioo.commons.memcache.spy.ops.GetOperation;
import com.dooioo.commons.memcache.spy.ops.GetlOperation;
import com.dooioo.commons.memcache.spy.ops.GetsOperation;
import com.dooioo.commons.memcache.spy.ops.KeyedOperation;
import com.dooioo.commons.memcache.spy.ops.MultiGetOperationCallback;
import com.dooioo.commons.memcache.spy.ops.MultiGetsOperationCallback;
import com.dooioo.commons.memcache.spy.ops.Mutator;
import com.dooioo.commons.memcache.spy.ops.MutatorOperation;
import com.dooioo.commons.memcache.spy.ops.NoopOperation;
import com.dooioo.commons.memcache.spy.ops.Operation;
import com.dooioo.commons.memcache.spy.ops.OperationCallback;
import com.dooioo.commons.memcache.spy.ops.SASLAuthOperation;
import com.dooioo.commons.memcache.spy.ops.SASLMechsOperation;
import com.dooioo.commons.memcache.spy.ops.SASLStepOperation;
import com.dooioo.commons.memcache.spy.ops.StatsOperation;
import com.dooioo.commons.memcache.spy.ops.StoreOperation;
import com.dooioo.commons.memcache.spy.ops.StoreType;
import com.dooioo.commons.memcache.spy.ops.TapOperation;
import com.dooioo.commons.memcache.spy.ops.UnlockOperation;
import com.dooioo.commons.memcache.spy.ops.VersionOperation;
import com.dooioo.commons.memcache.spy.ops.GetOperation.Callback;
import com.dooioo.commons.memcache.spy.tapmessage.RequestMessage;
import com.dooioo.commons.memcache.spy.tapmessage.TapOpcode;


/**
 * Factory for binary operations.
 */
public class BinaryOperationFactory extends BaseOperationFactory {

  public DeleteOperation
  delete(String key, OperationCallback operationCallback) {
    return new DeleteOperationImpl(key, operationCallback);
  }

  public UnlockOperation unlock(String key, long casId,
          OperationCallback cb) {
    return new UnlockOperationImpl(key, casId, cb);
  }
  public FlushOperation flush(int delay, OperationCallback cb) {
    return new FlushOperationImpl(cb);
  }

  public GetAndTouchOperation getAndTouch(String key, int expiration,
      GetAndTouchOperation.Callback cb) {
    return new GetAndTouchOperationImpl(key, expiration, cb);
  }

  public GetOperation get(String key, Callback callback) {
    return new GetOperationImpl(key, callback);
  }

  public GetOperation get(Collection<String> value, Callback cb) {
    return new MultiGetOperationImpl(value, cb);
  }

  public GetlOperation getl(String key, int exp, GetlOperation.Callback cb) {
    return new GetlOperationImpl(key, exp, cb);
  }

  public GetsOperation gets(String key, GetsOperation.Callback cb) {
    return new GetsOperationImpl(key, cb);
  }

  public MutatorOperation mutate(Mutator m, String key, long by, long def,
      int exp, OperationCallback cb) {
    return new MutatorOperationImpl(m, key, by, def, exp, cb);
  }

  public StatsOperation stats(String arg,
      com.dooioo.commons.memcache.spy.ops.StatsOperation.Callback cb) {
    return new StatsOperationImpl(arg, cb);
  }

  public StoreOperation store(StoreType storeType, String key, int flags,
      int exp, byte[] data, OperationCallback cb) {
    return new StoreOperationImpl(storeType, key, flags, exp, data, 0, cb);
  }

  public KeyedOperation touch(String key, int expiration,
      OperationCallback cb) {
    return new TouchOperationImpl(key, expiration, cb);
  }

  public VersionOperation version(OperationCallback cb) {
    return new VersionOperationImpl(cb);
  }

  public NoopOperation noop(OperationCallback cb) {
    return new NoopOperationImpl(cb);
  }

  public CASOperation cas(StoreType type, String key, long casId, int flags,
      int exp, byte[] data, OperationCallback cb) {
    return new StoreOperationImpl(type, key, flags, exp, data, casId, cb);
  }

  public ConcatenationOperation cat(ConcatenationType catType, long casId,
      String key, byte[] data, OperationCallback cb) {
    return new ConcatenationOperationImpl(catType, key, data, casId, cb);
  }

  @Override
  protected Collection<? extends Operation> cloneGet(KeyedOperation op) {
    Collection<Operation> rv = new ArrayList<Operation>();
    GetOperation.Callback getCb = null;
    GetsOperation.Callback getsCb = null;
    if (op.getCallback() instanceof GetOperation.Callback) {
      getCb =
          new MultiGetOperationCallback(op.getCallback(), op.getKeys().size());
    } else {
      getsCb =
          new MultiGetsOperationCallback(op.getCallback(), op.getKeys().size());
    }
    for (String k : op.getKeys()) {
      rv.add(getCb == null ? gets(k, getsCb) : get(k, getCb));
    }
    return rv;
  }

  public SASLAuthOperation saslAuth(String[] mech, String serverName,
      Map<String, ?> props, CallbackHandler cbh, OperationCallback cb) {
    return new SASLAuthOperationImpl(mech, serverName, props, cbh, cb);
  }

  public SASLMechsOperation saslMechs(OperationCallback cb) {
    return new SASLMechsOperationImpl(cb);
  }

  public SASLStepOperation saslStep(String[] mech, byte[] challenge,
      String serverName, Map<String, ?> props, CallbackHandler cbh,
      OperationCallback cb) {
    return new SASLStepOperationImpl(mech, challenge, serverName, props, cbh,
        cb);
  }

  public TapOperation tapBackfill(String id, long date, OperationCallback cb) {
    return new TapBackfillOperationImpl(id, date, cb);
  }

  public TapOperation tapCustom(String id, RequestMessage message,
      OperationCallback cb) {
    return new TapCustomOperationImpl(id, message, cb);
  }

  public TapOperation
  tapAck(TapOpcode opcode, int opaque, OperationCallback cb) {
    return new TapAckOperationImpl(opcode, opaque, cb);
  }

  public TapOperation tapDump(String id, OperationCallback cb) {
    return new TapDumpOperationImpl(id, cb);
  }
}
