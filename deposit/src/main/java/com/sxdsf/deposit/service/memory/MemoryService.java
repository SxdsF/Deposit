package com.sxdsf.deposit.service.memory;

import com.sxdsf.deposit.service.DepositService;
import com.sxdsf.deposit.service.memory.read.AsyncMemoryReadService;
import com.sxdsf.deposit.service.memory.read.SyncMemoryReadService;
import com.sxdsf.deposit.service.memory.write.AsyncMemoryWriteService;
import com.sxdsf.deposit.service.memory.write.SyncMemoryWriteService;

/**
 * 面向运存的服务，以key-value的形式，key的类型为String和int，value可以为任意类型
 * 
 * @author sunbowen
 * 
 */
public interface MemoryService extends DepositService {
	AsyncMemoryReadService asyncRead();

	SyncMemoryReadService syncRead();

	AsyncMemoryWriteService asyncWrite();

	SyncMemoryWriteService syncWrite();
}
