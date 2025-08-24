package club.huddleup

import club.huddleup.admin.UserSyncJob
import io.ktor.server.application.*
import org.quartz.*
import org.quartz.impl.StdSchedulerFactory
import java.util.*

fun Application.configureScheduler() {
    val scheduler: Scheduler = StdSchedulerFactory.getDefaultScheduler()
    scheduler.start()
    log.info("Scheduler started successfully...")

    scheduleUserSync(scheduler)

    // Shutdown scheduler gracefully
    monitor.subscribe(ApplicationStopPreparing) {
        scheduler.shutdown(true)
    }
}

private fun scheduleUserSync(scheduler: Scheduler) {
    val cronExpression = "0 0 1 * * ?" // Every day at 01:00 AM
    val jobDetail = JobBuilder.newJob(UserSyncJob::class.java)
        .withIdentity("userSyncJob", "adminJobs")
        .build()

    val trigger = TriggerBuilder.newTrigger()
        .withIdentity("userSyncTrigger", "adminJobs")
        .withSchedule(
            CronScheduleBuilder.cronSchedule(cronExpression)
                .inTimeZone(TimeZone.getTimeZone("UTC"))
        )
        .build()

    scheduler.scheduleJob(jobDetail, trigger)
}
