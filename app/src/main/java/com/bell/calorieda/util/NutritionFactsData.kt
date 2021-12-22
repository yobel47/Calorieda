@file:Suppress("unused")

package com.bell.calorieda.util

class NutritionFactsData(builder: Builder) {

    val servingSize: Double = builder.servingSize
    val calories: Double = builder.calories
    val totalFat: Double = builder.totalFat
    val saturatedFat: Double = builder.saturatedFat
    val cholesterol: Double = builder.cholesterol
    val sodium: Double = builder.sodium
    val totalCarbohydrates: Double = builder.totalCarbohydrates
    val dietaryFiber: Double = builder.dietaryFiber
    val sugars: Double = builder.sugars
    val protein: Double = builder.protein
    val potassium: Double = builder.potassium

    class Builder {
        var servingSize: Double = 0.0
        var calories: Double = 0.0
        var totalFat: Double = 0.0
        var saturatedFat: Double = 0.0
        var cholesterol: Double = 0.0
        var sodium: Double = 0.0
        var totalCarbohydrates: Double = 0.0
        var dietaryFiber: Double = 0.0
        var sugars: Double = 0.0
        var protein: Double = 0.0
        var potassium: Double = 0.0

        fun setServingSize(servingSize: Double?): Builder {
            this.servingSize = servingSize ?: 0.0
            return this
        }

        fun setCalories(calories: Double?): Builder {
            this.calories = calories ?: 0.0
            return this
        }

        fun setTotalFat(totalFat: Double?): Builder {
            this.totalFat = totalFat ?: 0.0
            return this
        }

        fun setSaturatedFat(saturatedFat: Double?): Builder {
            this.saturatedFat = saturatedFat ?: 0.0
            return this
        }

        fun setCholesterol(cholesterol: Double?): Builder {
            this.cholesterol = cholesterol ?: 0.0
            return this
        }

        fun setSodium(sodium: Double?): Builder {
            this.sodium = sodium ?: 0.0
            return this
        }

        fun setTotalCarbohydrates(totalCarbohydrates: Double?): Builder {
            this.totalCarbohydrates = totalCarbohydrates ?: 0.0
            return this
        }

        fun setDietaryFiber(dietaryFiber: Double?): Builder {
            this.dietaryFiber = dietaryFiber ?: 0.0
            return this
        }

        fun setSugars(sugars: Double?): Builder {
            this.sugars = sugars ?: 0.0
            return this
        }

        fun setProtein(protein: Double?): Builder {
            this.protein = protein ?: 0.0
            return this
        }

        fun setPotassium(potassium: Double?): Builder {
            this.potassium = potassium ?: 0.0
            return this
        }

        fun create(): NutritionFactsData {
            return NutritionFactsData(this)
        }
    }
}