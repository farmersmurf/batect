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

package batect.cli

import com.natpryce.hamkrest.absent
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object ValueOptionSpec : Spek({
    describe("a value option") {
        on("not applying a value for the option") {
            val option = ValueOption("value", "Some value")

            it("returns 'null' as the value") {
                assertThat(option.value, absent())
            }
        }

        on("applying a value for the option") {
            val option = ValueOption("value", "Some value")
            option.applyValue("abc123")

            it("returns that value as the value") {
                assertThat(option.value, equalTo("abc123"))
            }
        }
    }
})
