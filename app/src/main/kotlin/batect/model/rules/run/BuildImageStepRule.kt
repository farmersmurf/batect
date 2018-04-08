/*
   Copyright 2017-2018 Charles Korn.

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

package batect.model.rules.run

import batect.config.Container
import batect.model.events.TaskEvent
import batect.model.rules.TaskStepRule
import batect.model.rules.TaskStepRuleEvaluationResult
import batect.model.steps.BuildImageStep

data class BuildImageStepRule(val projectName: String, val container: Container) : TaskStepRule() {
    override fun evaluate(pastEvents: Set<TaskEvent>): TaskStepRuleEvaluationResult {
        return TaskStepRuleEvaluationResult.Ready(BuildImageStep(projectName, container))
    }

    override fun toString() = "${this::class.simpleName}(projectName=$projectName, container=${container.name})"
}
