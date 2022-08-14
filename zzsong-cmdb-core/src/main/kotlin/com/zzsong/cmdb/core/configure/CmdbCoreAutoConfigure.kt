package com.zzsong.cmdb.core.configure

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * @author 宋志宗 on 2022/8/13
 */
@EnableScheduling
@EnableReactiveMongoAuditing
@ComponentScan("com.zzsong.cmdb.core")
@EntityScan("com.zzsong.cmdb.core.domain.model")
@EnableReactiveMongoRepositories("com.zzsong.cmdb.core")
@EnableConfigurationProperties(CmdbProperties::class)
class CmdbCoreAutoConfigure {

}
