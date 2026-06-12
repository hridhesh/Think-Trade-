package com.example.data

object TradingData {
    val candlestickLessons = listOf(
        // LEVEL 1: BEGINNER (Single Candle Basics)
        PatternLesson(
            id = "candle_lvl1_hammer",
            title = "Hammer Pattern",
            category = "candlestick",
            level = 1,
            shortDesc = "Classic bullish reversal candle with a long lower shadow.",
            description = "A Hammer occurs at the bottom of a downtrend, showing that sellers drove prices lower but ultimately buyers fought back to push the closing price near the high.",
            patternType = "hammer",
            marketSignal = "Bullish Reversal",
            howToTrade = "Wait for the hammer to form at a clear support line. Place a buy order above the high of the hammer candle, and a stop-loss just below the low of the lower wick.",
            rewardPoints = 50
        ),
        PatternLesson(
            id = "candle_lvl1_shooting_star",
            title = "Shooting Star Pattern",
            category = "candlestick",
            level = 1,
            shortDesc = "Bearish counterpart of the hammer, showing rejected highs.",
            description = "A Shooting Star appears at the top of an uptrend. Buyers drove prices up in a strong rally, but sellers took control to force the close back down toward the open.",
            patternType = "shooting_star",
            marketSignal = "Bearish Reversal",
            howToTrade = "Wait for the shooting star to reject a major resistance zone. Enter a short sell trade once price breaks below the body of the shooting star, placing your stop-loss just above the upper shadow.",
            rewardPoints = 50
        ),
        PatternLesson(
            id = "candle_lvl1_doji",
            title = "Doji Pattern",
            category = "candlestick",
            level = 1,
            shortDesc = "Perfect cross representing extreme market indecision.",
            description = "A Doji forms when opening and closing prices are virtually equal. It signifies a complete standoff between buyers and sellers, often signaling exhaustion of the prevailing trend.",
            patternType = "doji",
            marketSignal = "Trend Indecision",
            howToTrade = "A Doji is not an entry signal by itself. Wait for confirmation in the next few candles. An engulfing candle right after a Doji often indicates the new trend direction.",
            rewardPoints = 50
        ),

        // LEVEL 2: INTERMEDIATE (Multi-Candle Combinations)
        PatternLesson(
            id = "candle_lvl2_bullish_engulfing",
            title = "Bullish Engulfing",
            category = "candlestick",
            level = 2,
            shortDesc = "Two-candle reversal: buyers fully overwhelm the previous day.",
            description = "The first candle is small and bearish (red), followed by a massive bullish candle (green) whose real body completely covers or 'engulfs' the body of the first candle.",
            patternType = "bullish_engulfing",
            marketSignal = "Bullish Reversal",
            howToTrade = "This pattern is highly reliable when it forms after a sustained downtrend. Buy at the opening of the third candle, placing your stop-loss below the second engulfing candle's low.",
            rewardPoints = 75
        ),
        PatternLesson(
            id = "candle_lvl2_bearish_engulfing",
            title = "Bearish Engulfing",
            category = "candlestick",
            level = 2,
            shortDesc = "Two-candle reversal: aggressive selling overrides buying power.",
            description = "A small green candle is completely consumed by a subsequent, much larger red candle, indicating robust sell supply hitting the market and ending the uptrend.",
            patternType = "bearish_engulfing",
            marketSignal = "Bearish Reversal",
            howToTrade = "Sell or enter shorts below the low of the engulfing red candle, setting the stop-loss above the high of the green or red candle (whichever is higher).",
            rewardPoints = 75
        ),
        PatternLesson(
            id = "candle_lvl2_morning_star",
            title = "Morning Star",
            category = "candlestick",
            level = 2,
            shortDesc = "Three-candle pattern representing hope rising after darkness.",
            description = "A morning star is a bullish three-candle pattern. It starts with a long red candle, followed by a gap down minor 'star' candle, and ends with a robust green candle closing past the midpoint of the first one.",
            patternType = "morning_star",
            marketSignal = "Bullish Reversal (High Reliability)",
            howToTrade = "Look for this near critical support zones. Buy on the close of the third candle (the confirmation green candle). Place a stop-loss under the lowest point is the middle star candle.",
            rewardPoints = 75
        ),

        // LEVEL 3: ADVANCED (Complex Trend Exhaustion)
        PatternLesson(
            id = "candle_lvl3_evening_star",
            title = "Evening Star",
            category = "candlestick",
            level = 3,
            shortDesc = "Three-candle bearish reversal indicating distribution highs.",
            description = "The bearish counterpart of the morning star. A long green candle is followed by a gapped up star, and then closed out by a red candle which sinks down and damages the previous uptrend structure.",
            patternType = "evening_star",
            marketSignal = "Bearish Reversal",
            howToTrade = "Sell on the confirmation closing bar of the third candle. Your stop-loss goes above the highest tip of the central star candle. Target major swing low areas.",
            rewardPoints = 100
        ),
        PatternLesson(
            id = "candle_lvl3_three_soldiers",
            title = "Three White Soldiers",
            category = "candlestick",
            level = 3,
            shortDesc = "Strong trend ignition characterized by stable upward drives.",
            description = "Composed of three consecutive long green bodies, each opening within the previous candle's body and closing near its daily maximum, showing intense buy side momentum.",
            patternType = "three_soldiers",
            marketSignal = "Strong Bullish Continuation",
            howToTrade = "Do not chase at the very top. Look for a small pullback or 'throwback' to the third soldier's open level, then enter long with a tight stop-loss below the second soldier's low.",
            rewardPoints = 100
        ),
        PatternLesson(
            id = "candle_lvl3_three_crows",
            title = "Three Black Crows",
            category = "candlestick",
            level = 3,
            shortDesc = "Persistent selling pressure that breaks bull support blocks.",
            description = "Three consecutive long red bodies closing progressively lower, with small or non-existent wicks. This shows a complete and aggressive regime takeover by professional short sellers.",
            patternType = "three_crows",
            marketSignal = "Strong Bearish Continuation",
            howToTrade = "Sell short on the third candle close or wait for a brief squeeze pullback. Stop-loss should reside tightly above the first crow's high to protect capital.",
            rewardPoints = 100
        )
    )

    val chartLessons = listOf(
        // LEVEL 1: BEGINNER (Support, Resistance, Triangles)
        PatternLesson(
            id = "chart_lvl1_support_res",
            title = "Support & Resistance",
            category = "chart",
            level = 1,
            shortDesc = "The foundation of all technical chart analysis.",
            description = "Support is a horizontal floor where buying interest historically overcomes selling, and price bounces back up. Resistance is a ceiling where supply overtakes demand, pushing prices back down.",
            patternType = "support_resistance",
            marketSignal = "Range Trading",
            howToTrade = "Buy near the support floor when strong confirmation candles (like a Hammer) appear. Conversely, sell or short when price fails to break past the resistance ceiling.",
            rewardPoints = 50
        ),
        PatternLesson(
            id = "chart_lvl1_ascending_triangle",
            title = "Ascending Triangle",
            category = "chart",
            level = 1,
            shortDesc = "A constructive bullish pattern with a flat top and rising lows.",
            description = "Buyers are gradually getting more aggressive, creating higher swing lows, while sellers maintain a firm wall of limit order resistance at a horizontal ceiling.",
            patternType = "ascending_triangle",
            marketSignal = "Bullish Breakout",
            howToTrade = "Enter a buy transaction when a daily candle closes cleanly *above* the horizontal resistance ceiling. Keep a stop-loss inside the triangle just below the last rising low swing.",
            rewardPoints = 50
        ),
        PatternLesson(
            id = "chart_lvl1_descending_triangle",
            title = "Descending Triangle",
            category = "chart",
            level = 1,
            shortDesc = "A bearish consolidation with a flat floor and falling highs.",
            description = "Sellers are driving prices down, making lower swing highs, whilst buyers defend a clear horizontal support line. Exhaustion of the floor triggers major waterfalls.",
            patternType = "descending_triangle",
            marketSignal = "Bearish Breakdown",
            howToTrade = "Enter a short or sell order upon a definitive breakout close below the lower support line. Protect with a stop-loss placed above the intermediate descending high.",
            rewardPoints = 50
        ),

        // LEVEL 2: INTERMEDIATE (Double Tops & Bottoms, Head-and-Shoulders)
        PatternLesson(
            id = "chart_lvl2_double_bottom",
            title = "Double Bottom (W)",
            category = "chart",
            level = 2,
            shortDesc = "A powerful 'W' shaped trend reversal floor.",
            description = "The market drops to hit a low zone, experiences a mild rally, then tests the same low point a second time and holds strong, turning back up in aggressive buying.",
            patternType = "double_bottom",
            marketSignal = "Bullish Reversal",
            howToTrade = "Measure the depth of the bottom. Once the asset breaks above the high boundary between the two bottoms (the neckline), enter long. Target distance is equal to the pattern's depth.",
            rewardPoints = 75
        ),
        PatternLesson(
            id = "chart_lvl2_double_top",
            title = "Double Top (M)",
            category = "chart",
            level = 2,
            shortDesc = "An 'M' shaped bearish distribution pattern.",
            description = "Two prominent peaks at a matching high resistance point. Buyers fail twice to breakthrough, leading to a capitulation below the intermediate support line (neckline).",
            patternType = "double_top",
            marketSignal = "Bearish Reversal",
            howToTrade = "Short are triggered immediately when price breaks under the neckline support of the central valley. Stop-loss remains above the second highest peak peak to absorb noise.",
            rewardPoints = 75
        ),
        PatternLesson(
            id = "chart_lvl2_head_shoulders",
            title = "Head & Shoulders",
            category = "chart",
            level = 2,
            shortDesc = "The ultimate crown pinnacle reversal of an uptrend.",
            description = "Consists of three peaks: a left shoulder, a higher central peak (the head), and a lower third peak (the right shoulder). The supportive trendline below peaks is called the neck-line.",
            patternType = "head_shoulders",
            marketSignal = "Bearish Trend Reversal",
            howToTrade = "Draw the neckline. Trigger short entries upon a definitive closed bar below the neckline. Stop-loss goes at the high of the right shoulder.",
            rewardPoints = 75
        ),

        // LEVEL 3: ADVANCED (Inverse H&S, Flags, Cups)
        PatternLesson(
            id = "chart_lvl3_inverse_hs",
            title = "Inverse Head & Shoulders",
            category = "chart",
            level = 3,
            shortDesc = "An upside-down Head & Shoulders signifying major accumulation.",
            description = "Three downside troughs where the central trough is lower than the surrounding shoulders. A clean upward breakout above the neckline signals the launch of a new bull run.",
            patternType = "inverse_head_shoulders",
            marketSignal = "Bullish Trend Reversal",
            howToTrade = "Wait for a high-volume breakout closing above the neckline slope. Enter a long-term swing position with the stop-loss slightly beneath the right shoulder trough.",
            rewardPoints = 100
        ),
        PatternLesson(
            id = "chart_lvl3_bullish_pennant",
            title = "Bullish Pennant",
            category = "chart",
            level = 3,
            shortDesc = "A hyper-aggressive momentum flag during vertical rallies.",
            description = "A swift vertical surge in price (the flagpole) followed by a super compact, narrow triangular consolidation (pennant) before another explosive expansion upwards.",
            patternType = "bullish_pennant",
            marketSignal = "Strong Bullish Continuation",
            howToTrade = "Enter immediately on a breakout of the upper descending wedge line of the pennant. Target is the length of the flagpole projected upward from the breakout point.",
            rewardPoints = 100
        ),
        PatternLesson(
            id = "chart_lvl3_cup_handle",
            title = "Cup & Handle",
            category = "chart",
            level = 3,
            shortDesc = "U-shaped rounded accumulation with a brief correction handle.",
            description = "A long, rounded 'U' bowl representing consolidation, followed by a tight parallel downward handle. This represents a mature bullish consolidation before final breakouts.",
            patternType = "cup_handle",
            marketSignal = "Bullish Trend Continuation",
            howToTrade = "Identify the resistance line connecting the cup rim. Enter buy trades when the asset breaks out above the handle's upper boundary and clears the rim level.",
            rewardPoints = 100
        )
    )

    val quizQuestions = listOf(
        // LEVEL 1: BEGINNER
        QuizQuestion(
            id = "quiz_lvl1_q1",
            level = 1,
            category = "candlestick",
            questionText = "Which candlestick pattern is characterized by a very long lower wick, a small real body near the top of the range, and forms at the end of a downtrend?",
            options = listOf("Shooting Star", "Doji", "Hammer", "Engulfing"),
            correctAnswerIndex = 2,
            explanation = "A Hammer is a bullish reversal candlestick with a long lower wick (at least twice the size of the body) that appears at the bottom of a downtrend, representing a rejection of lower prices."
        ),
        QuizQuestion(
            id = "quiz_lvl1_q2",
            level = 1,
            category = "chart",
            questionText = "What term is used in technical analysis to describe a price floor where buying interest historically prevents prices from dropping further?",
            options = listOf("Resistance", "Neckline", "Support", "Handle"),
            correctAnswerIndex = 2,
            explanation = "Support is a price level where demand (buying power) is strong enough to prevent the price from declining further, acting as a historical floor."
        ),
        QuizQuestion(
            id = "quiz_lvl1_q3",
            level = 1,
            category = "chart",
            questionText = "An Ascending Triangle pattern is typical of buyers becoming increasingly aggressive. What visual feature shows this?",
            options = listOf("Symmetric converging lines", "A flat horizontal resistance ceiling with higher swing lows", "A flat support floor with lower swing highs", "Two consecutive identical peaks"),
            correctAnswerIndex = 1,
            explanation = "The Ascending Triangle consists of a flat horizontal resistance roof (selling limit orders) and a series of rising swing lows (buyers aggressively accumulating at higher levels)."
        ),
        QuizQuestion(
            id = "quiz_lvl1_q4",
            level = 1,
            category = "candlestick",
            questionText = "If a candlestick opens at $100, rallies to $105, drops to $95, and closes exactly at $100.05, what kind of candle is it?",
            options = listOf("Hammer", "Doji", "Shooting Star", "Marubozu"),
            correctAnswerIndex = 1,
            explanation = "A Doji is formed when the opening and closing prices are almost exactly equal, resembling a cross or plus sign and representing perfect market indecision."
        ),

        // LEVEL 2: INTERMEDIATE
        QuizQuestion(
            id = "quiz_lvl2_q1",
            level = 2,
            category = "candlestick",
            questionText = "In a Bullish Engulfing pattern, which of the following is true?",
            options = listOf(
                "A large green candle is followed by a small green candle.",
                "The second candle's body completely covers the first candle's body.",
                "Three black candles are followed by a pin bar.",
                "A small red candle's body engulfs a large green body."
            ),
            correctAnswerIndex = 1,
            explanation = "In a Bullish Engulfing pattern, a small bearish red candle is followed by a larger bullish green candle whose body completely covers or 'engulfs' the body of the previous day's candle."
        ),
        QuizQuestion(
            id = "quiz_lvl2_q2",
            level = 2,
            category = "chart",
            questionText = "A 'Double Top' pattern resembles which letter of the alphabet, and what signal does it give?",
            options = listOf("W, signaling a bullish reversal", "M, signaling a bearish reversal", "V, signaling a continuation", "H, signaling a breakout"),
            correctAnswerIndex = 1,
            explanation = "A Double Top resembles the letter 'M' because the price tests a resistance peak twice, fails to break out, then drops below the intermediate support neck line to reverse bearishly."
        ),
        QuizQuestion(
            id = "quiz_lvl2_q3",
            level = 2,
            category = "chart",
            questionText = "Which technical pattern consists of three distinct high peaks, where the central peak is the highest and the surrounding two peaks are slightly lower and matching?",
            options = listOf("W Bottom", "Head & Shoulders", "Ascending Triangle", "Cup & Handle"),
            correctAnswerIndex = 1,
            explanation = "The Head & Shoulders pattern consists of three peaks: a left shoulder, a higher peak (head), and a lower third peak (right shoulder), signaling a transition from an uptrend to a downtrend."
        ),
        QuizQuestion(
            id = "quiz_lvl2_q4",
            level = 2,
            category = "candlestick",
            questionText = "The Morning Star three-candle pattern is a powerful reversal, typically consisting of:",
            options = listOf(
                "Three consecutive green candles in a row.",
                "A green candle, a gap down Doji, and a huge red candle.",
                "A long red candle, a small star/indecision candle, and a strong green candle.",
                "Two consecutive hammers and a shooting star."
            ),
            correctAnswerIndex = 2,
            explanation = "A Morning Star starts with a long red candle (sellers in control), a short-bodied middle candle (indecision star), and a strong green candle closing deep into the first candle's range."
        ),

        // LEVEL 3: ADVANCED
        QuizQuestion(
            id = "quiz_lvl3_q1",
            level = 3,
            category = "chart",
            questionText = "What is the primary visual difference between a Head & Shoulders and an Inverse Head & Shoulders pattern?",
            options = listOf(
                "Inverse Head & Shoulders occurs at trend peaks to trigger crash sales.",
                "Inverse Head & Shoulders is flipped upside-down, occurring at trend bottoms to mark bullish reversals.",
                "Head & Shoulders has a flat diagonal line and Inverse H&S is perfectly horizontal.",
                "There is no visual difference, they indicate the same exact market event."
            ),
            correctAnswerIndex = 1,
            explanation = "An Inverse Head & Shoulders is the upside-down equivalent of a standard Head & Shoulders pattern. It occurs at the bottom of a downtrend and serves as a highly reliable signal of a major bullish reversal."
        ),
        QuizQuestion(
            id = "quiz_lvl3_q2",
            level = 3,
            category = "candlestick",
            questionText = "The 'Three Black Crows' candlestick pattern indicates what kind of market sentiment?",
            options = listOf(
                "Gradual bullish absorption over a tight trading week.",
                "Extreme bearish momentum with consecutive low closures, signifying aggressive trend exhaustion.",
                "Market indecision that is about to breakout sideways.",
                "A highly volatile short squeeze that hurts overall market liquidations."
            ),
            correctAnswerIndex = 1,
            explanation = "Three Black Crows consists of three consecutive long-bodied red candles closing progressively lower, indicating that strong sellers have taken full control and a major downtrend is underway."
        ),
        QuizQuestion(
            id = "quiz_lvl3_q3",
            level = 3,
            category = "chart",
            questionText = "In a standard 'Cup and Handle' pattern, how is the 'handle' expected to behave before a bullish breakout?",
            options = listOf(
                "Like a fast vertical pump to double the size of the cup's depth.",
                "Like a minor, downward-sloping consolidated trading flag that is brief in duration.",
                "Like a rounded bowl that has a flat horizon support line.",
                "Like a giant erratic spike that causes heavy wick rejections."
            ),
            correctAnswerIndex = 1,
            explanation = "The 'handle' of a Cup and Handle pattern is composed of a brief downward-sloping consolidation wedge or channel, showing a low-volume shakeout of weak sellers before the final explosive bullish breakout."
        )
    )

    val achievements = listOf(
        Achievement("ach_rookie", "Trading Apprentice", "Reach a total of 150 lesson and quiz points.", 150, "school"),
        Achievement("ach_intermediate", "Market Specialist", "Reach a total of 500 lesson and quiz points.", 500, "trending_up"),
        Achievement("ach_pro", "Prop Firm Master", "Reach a total of 1000 lesson and quiz points.", 1000, "workspace_premium")
    )
}
