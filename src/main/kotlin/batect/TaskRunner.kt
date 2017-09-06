/*
   Copyright 2017 Charles Korn.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package batect

import batect.config.Configuration
import batect.model.DependencyGraphProvider
import batect.model.TaskStateMachineProvider

data class TaskRunner(
        private val eventLogger: EventLogger,
        private val graphProvider: DependencyGraphProvider,
        private val stateMachineProvider: TaskStateMachineProvider,
        private val executionManagerProvider: ParallelExecutionManagerProvider
) {
    fun run(config: Configuration, taskName: String): Int {
        val resolvedTask = config.tasks[taskName]

        if (resolvedTask == null) {
            eventLogger.logTaskDoesNotExist(taskName)
            return -1
        }

        val graph = graphProvider.createGraph(config, resolvedTask)
        val stateMachine = stateMachineProvider.createStateMachine(graph)
        val executionManager = executionManagerProvider.createParallelExecutionManager(stateMachine, taskName)

        eventLogger.reset()

        return executionManager.run()
    }
}
