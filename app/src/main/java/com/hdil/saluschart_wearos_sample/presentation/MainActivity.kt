package com.hdil.saluschart_wearos_sample.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.material3.Text
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import androidx.wear.compose.ui.tooling.preview.WearPreviewFontScales
import com.hdil.saluschart.core.chart.ChartMark
import com.hdil.saluschart.core.chart.ProgressChartMark
import com.hdil.saluschart.data.model.model.SleepSession
import com.hdil.saluschart.data.model.model.SleepStage
import com.hdil.saluschart.data.model.model.SleepStageType
import com.hdil.saluschart.ui.wear.compose.WearMinimalActivityRing
import com.hdil.saluschart.ui.wear.compose.WearMinimalGaugeSegment
import com.hdil.saluschart.ui.wear.compose.WearMinimalMultiSegmentGaugeChart
import com.hdil.saluschart.ui.wear.compose.WearPieChart
import com.hdil.saluschart.ui.wear.compose.WearSleepStageChart
import com.hdil.saluschart.ui.theme.LocalSalusChartColors
import com.hdil.saluschart.ui.theme.SalusChartColorScheme
import com.hdil.saluschart_wearos_sample.presentation.theme.SalusChartwearossampleTheme
import java.time.LocalDateTime
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlinx.coroutines.delay

private val SamsungOrange = Color(0xFFFF7A3D)
private val TrackGray = Color(0xFF242424)
private val LabelGray = Color(0xFFA9ADB7)
private val SleepAwake = Color(0xFFFF7D6C)
private val SleepRem = Color(0xFF42D5F5)
private val SleepCore = Color(0xFF159DFF)
private val SleepDeep = Color(0xFF3D3ABB)
private val SleepStageScheme = SalusChartColorScheme(
    primary = SleepCore,
    secondary = LabelGray,
    palette = listOf(SleepDeep, SleepCore, SleepRem, SleepAwake),
)
private val ClockFormatter = DateTimeFormatter.ofPattern("HH:mm")
private val DateFormatter = DateTimeFormatter.ofPattern("d EEE", Locale.ENGLISH)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp() {
    SalusChartwearossampleTheme {
        AppScaffold {
            val pagerState = rememberPagerState(pageCount = { 5 })
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
            ) { page ->
                when (page) {
                    0 -> WatchHomeScreen()
                    1 -> HeartRateScreen(bpm = 69, knobPosition = 0.54f)
                    2 -> StressScreen()
                    3 -> SleepScoreScreen()
                    else -> SleepStageScreen()
                }
            }
        }
    }
}

@Composable
private fun WatchHomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 24.dp, vertical = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(2.dp))
        WearMinimalActivityRing(
            data = listOf(
                ProgressChartMark(0.0, 0.92, 1.0, "Outer"),
                ProgressChartMark(1.0, 0.78, 1.0, "Middle"),
                ProgressChartMark(2.0, 0.58, 1.0, "Inner"),
            ),
            modifier = Modifier.fillMaxWidth(0.72f),
            colors = listOf(
                Color(0xFF00E85D),
                Color(0xFF4C8CFF),
                Color(0xFFFF1493),
            ),
            strokeWidth = 12.dp,
            ringGap = 5.dp,
            chartHeight = 108.dp,
            trackAlpha = 0.22f,
        )
        Spacer(modifier = Modifier.weight(1f))
        CurrentTimeFooter()
    }
}

@Composable
private fun CurrentTimeFooter() {
    var now by remember { mutableStateOf(LocalDateTime.now()) }

    LaunchedEffect(Unit) {
        while (true) {
            now = LocalDateTime.now()
            delay(1_000L)
        }
    }

    Text(
        text = now.format(ClockFormatter),
        color = Color.White,
        fontSize = 38.sp,
        lineHeight = 38.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
    )
    Text(
        text = now.format(DateFormatter).uppercase(Locale.ENGLISH),
        color = Color.White,
        fontSize = 16.sp,
        lineHeight = 19.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun HeartRateScreen(bpm: Int, knobPosition: Float) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 24.dp, vertical = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "♥",
                color = SamsungOrange,
                fontSize = 25.sp,
                lineHeight = 25.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Now",
                color = SamsungOrange,
                fontSize = 18.sp,
                lineHeight = 21.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = bpm.toString(),
                color = Color.White,
                fontSize = 54.sp,
                lineHeight = 54.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "bpm",
                color = Color.White,
                fontSize = 25.sp,
                lineHeight = 29.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp),
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier.padding(bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            SalusMinMaxBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp),
                knobPosition = knobPosition,
                rangeStart = 0.42f,
                rangeEnd = 0.62f,
                rangeColor = Color(0xFFFF2E23),
            )
            Text(
                text = "Daily min-max",
                color = LabelGray,
                fontSize = 15.sp,
                lineHeight = 18.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun StressScreen() {
    val stressMarkerRatio = 0.1f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 24.dp, vertical = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "⚡",
            color = Color(0xFFFF6B24),
            fontSize = 25.sp,
            lineHeight = 25.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "Now",
            color = Color(0xFFFF6B24),
            fontSize = 18.sp,
            lineHeight = 21.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Relaxed",
            color = Color.White,
            fontSize = 38.sp,
            lineHeight = 42.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(10.dp))
        WearMinimalMultiSegmentGaugeChart(
            segments = listOf(
                WearMinimalGaugeSegment(0.24f, Color(0xFF19BFFF)),
                WearMinimalGaugeSegment(0.26f, Color(0xFF78E23F)),
                WearMinimalGaugeSegment(0.13f, Color(0xFFFFDD20)),
                WearMinimalGaugeSegment(0.11f, Color(0xFFFFD12A)),
                WearMinimalGaugeSegment(0.26f, Color(0xFFFF951F)),
            ),
            markerRatio = stressMarkerRatio,
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp),
            chartHeight = 9.dp,
            markerWidth = 14.dp,
            markerColor = Color.White,
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun SalusMinMaxBar(
    modifier: Modifier,
    knobPosition: Float,
    rangeStart: Float,
    rangeEnd: Float,
    rangeColor: Color,
) {
    WearMinimalMultiSegmentGaugeChart(
        segments = listOf(
            WearMinimalGaugeSegment(rangeStart.coerceIn(0f, 1f), TrackGray),
            WearMinimalGaugeSegment((rangeEnd - rangeStart).coerceIn(0f, 1f), rangeColor),
            WearMinimalGaugeSegment((1f - rangeEnd).coerceIn(0f, 1f), TrackGray),
        ),
        markerRatio = knobPosition,
        modifier = modifier,
        chartHeight = 9.dp,
        markerWidth = 14.dp,
        markerColor = Color.White,
    )
}

@Composable
private fun SleepScoreScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF001429))
            .padding(28.dp),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier.size(128.dp),
            contentAlignment = Alignment.Center,
        ) {
            WearPieChart(
                data = listOf(
                    ChartMark(0.0, 34.0, "Deep"),
                    ChartMark(1.0, 46.0, "Light"),
                    ChartMark(2.0, 20.0, "REM"),
                ),
                modifier = Modifier.fillMaxWidth(),
                colors = listOf(
                    Color(0xFF4868FF),
                    Color(0xFF18D6C7),
                    Color(0xFFFF7D6C),
                ),
                chartHeight = 128.dp,
                ringThicknessRatio = 0.28f,
                chartScaleRatio = 0.86f,
                showCenterLabel = false,
                showBreakdown = false,
            )
            Text(
                text = "90",
                color = Color.White,
                fontSize = 42.sp,
                lineHeight = 42.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun SleepStageScreen() {
    val session = sampleSleepSession()

    CompositionLocalProvider(LocalSalusChartColors provides SleepStageScheme) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF001429))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 18.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color(0xFF123EA5), CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "",
                        color = Color(0xFF8FA2FF),
                        fontSize = 20.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Sleep Stages",
                        color = Color(0xFF8A8BFF),
                        fontSize = 21.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            Spacer(modifier = Modifier.height(22.dp))
            WearSleepStageChart(
                sleepSession = session,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(92.dp),
                showTitle = false,
                showLabels = false,
                showXAxis = false,
                showXAxisLabels = false,
                showYAxis = false,
                showYAxisLabels = false,
                showStartEndLabels = false,
                contentPadding = PaddingValues(0.dp),
                barHeightRatio = 0.58f,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "APR 1",
                color = LabelGray,
                fontSize = 14.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(10.dp))
            SleepStageLegendRow("Awake", "11m", SleepAwake)
            SleepStageLegendRow("REM", "2h 57m", SleepRem)
            SleepStageLegendRow("Core", "3h 43m", SleepCore)
            SleepStageLegendRow("Deep", "20m", SleepDeep)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun SleepStageLegendRow(label: String, value: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(9.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(color, CircleShape),
            )
            Text(
                text = label,
                color = Color.White,
                fontSize = 17.sp,
                lineHeight = 20.sp,
            )
        }
        Text(
            text = value,
            color = LabelGray,
            fontSize = 17.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

private fun sampleSleepSession(): SleepSession {
    val start = Instant.parse("2026-04-04T22:46:00Z")

    fun stage(startMinutes: Long, endMinutes: Long, type: SleepStageType): SleepStage =
        SleepStage(
            startTime = start.plusSeconds(startMinutes * 60),
            endTime = start.plusSeconds(endMinutes * 60),
            stage = type,
        )

    val stages = listOf(
        stage(0, 18, SleepStageType.AWAKE),
        stage(18, 54, SleepStageType.LIGHT),
        stage(54, 92, SleepStageType.DEEP),
        stage(92, 132, SleepStageType.LIGHT),
        stage(132, 156, SleepStageType.REM),
        stage(156, 214, SleepStageType.LIGHT),
        stage(214, 248, SleepStageType.DEEP),
        stage(248, 308, SleepStageType.LIGHT),
        stage(308, 344, SleepStageType.REM),
        stage(344, 404, SleepStageType.LIGHT),
        stage(404, 424, SleepStageType.AWAKE),
        stage(424, 462, SleepStageType.REM),
    )

    return SleepSession(
        startTime = stages.first().startTime,
        endTime = stages.last().endTime,
        stages = stages,
    )
}

@WearPreviewDevices
@WearPreviewFontScales
@Composable
fun DefaultPreview() {
    WearApp()
}
