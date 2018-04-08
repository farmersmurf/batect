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

import batect.model.rules.TaskStepRuleEvaluationResult
import batect.model.steps.CreateTaskNetworkStep
import batect.testutils.equalTo
import com.natpryce.hamkrest.assertion.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object CreateTaskNetworkStepRuleSpec : Spek({
    describe("a create task network step rule") {
        val rule = CreateTaskNetworkStepRule

        on("evaluating the rule") {
            val result = rule.evaluate(emptySet())

            it("returns a 'create task network' step") {
                assertThat(result, equalTo(TaskStepRuleEvaluationResult.Ready(CreateTaskNetworkStep)))
            }
        }
    }
})
