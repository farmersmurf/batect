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

import batect.config.BuildImage
import batect.config.Container
import batect.config.PortMapping
import batect.config.PullImage
import batect.docker.DockerImage
import batect.docker.DockerNetwork
import batect.model.events.ImageBuiltEvent
import batect.model.events.ImagePulledEvent
import batect.model.events.TaskEvent
import batect.model.events.TaskNetworkCreatedEvent
import batect.model.rules.TaskStepRule
import batect.model.rules.TaskStepRuleEvaluationResult
import batect.model.steps.CreateContainerStep
import batect.os.Command

data class CreateContainerStepRule(
    val container: Container,
    val command: Command?,
    val additionalEnvironmentVariables: Map<String, String>,
    val additionalPortMappings: Set<PortMapping>
) : TaskStepRule() {
    override fun evaluate(pastEvents: Set<TaskEvent>): TaskStepRuleEvaluationResult {
        val network = findNetwork(pastEvents)
        val image = findImage(pastEvents)

        if (network == null || image == null) {
            return TaskStepRuleEvaluationResult.NotReady
        }

        return TaskStepRuleEvaluationResult.Ready(CreateContainerStep(
            container,
            command,
            additionalEnvironmentVariables,
            additionalPortMappings,
            image,
            network
        ))
    }

    private fun findNetwork(pastEvents: Set<TaskEvent>): DockerNetwork? =
        pastEvents.singleInstanceOrNull<TaskNetworkCreatedEvent>()
            ?.network

    private fun findImage(pastEvents: Set<TaskEvent>): DockerImage? {
        return when (container.imageSource) {
            is PullImage -> pastEvents
                .singleInstanceOrNull<ImagePulledEvent> { it.image.id == container.imageSource.imageName }
                ?.image
            is BuildImage -> pastEvents
                .singleInstanceOrNull<ImageBuiltEvent> { it.container == container }
                ?.image
        }
    }

    override fun toString() = "${this::class.simpleName}(container=${container.name}, command=$command, additionalEnvironmentVariables=$additionalEnvironmentVariables, additionalPortMappings=$additionalPortMappings)"
}
