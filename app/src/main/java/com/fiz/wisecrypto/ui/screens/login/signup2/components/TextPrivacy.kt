package com.fiz.wisecrypto.ui.screens.login.signup2.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.fiz.wisecrypto.R

@Composable
fun TextPrivacy(
    showTermsAndConditions: () -> Unit = { },
    showPrivacyPolicy: () -> Unit = { },
    showContentPolicy: () -> Unit = { },
) {
    val annotatedText = buildAnnotatedString {
        append(stringResource(R.string.signup2_privacy1))
        append(" ")

        pushStringAnnotation(
            tag = "terms_conditions",
            annotation = "terms_conditions"
        )
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
            )
        ) {
            append(stringResource(R.string.signup2_terms_conditions))
        }
        pop()


        append(stringResource(R.string.signup2_privacy2))
        append(" ")

        pushStringAnnotation(
            tag = "privacy_policy",
            annotation = "privacy_policy"
        )
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
            )
        ) {
            append(stringResource(R.string.signup2_privacy_policy))
        }
        pop()

        append(" ")
        append(stringResource(R.string.signup2_privacy3))
        append(" ")

        pushStringAnnotation(
            tag = "content_policy",
            annotation = "content_policy"
        )
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
            )
        ) {
            append(stringResource(R.string.signup2_content_policy))
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        style = MaterialTheme.typography.bodyMedium,
        onClick = { offset ->

            annotatedText.getStringAnnotations(
                tag = "terms_conditions", start = offset, end = offset
            )
                .firstOrNull()?.let {
                    showTermsAndConditions()
                }

            annotatedText.getStringAnnotations(
                tag = "privacy_policy", start = offset, end = offset
            )
                .firstOrNull()?.let {
                    showPrivacyPolicy()
                }

            annotatedText.getStringAnnotations(
                tag = "content_policy", start = offset, end = offset
            )
                .firstOrNull()?.let {
                    showContentPolicy()
                }
        },
    )
}