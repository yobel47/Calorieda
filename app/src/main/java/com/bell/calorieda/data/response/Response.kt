package com.bell.calorieda.data.response

import com.google.gson.annotations.SerializedName

data class Response(

    @field:SerializedName("foods")
    val foods: ArrayList<FoodsItem>? = null
)

data class FoodsItem(

    @field:SerializedName("food_name")
    val foodName: String? = null,

    @field:SerializedName("nf_saturated_fat")
    val nfSaturatedFat: Double? = null,

    @field:SerializedName("metadata")
    val metadata: Metadata? = null,

    @field:SerializedName("nf_cholesterol")
    val nfCholesterol: Double? = null,

    @field:SerializedName("sub_recipe")
    val subRecipe: Any? = null,

    @field:SerializedName("nix_brand_id")
    val nixBrandId: Any? = null,

    @field:SerializedName("nf_potassium")
    val nfPotassium: Double? = null,

    @field:SerializedName("meal_type")
    val mealType: Int? = null,

    @field:SerializedName("nf_total_fat")
    val nfTotalFat: Double? = null,

    @field:SerializedName("nf_sugars")
    val nfSugars: Double? = null,

    @field:SerializedName("nf_protein")
    val nfProtein: Double? = null,

    @field:SerializedName("source")
    val source: Any? = null,

    @field:SerializedName("nix_item_id")
    val nixItemId: Any? = null,

    @field:SerializedName("ndb_no")
    val ndbNo: Any? = null,

    @field:SerializedName("brick_code")
    val brickCode: Any? = null,

    @field:SerializedName("serving_unit")
    val servingUnit: String? = null,

    @field:SerializedName("alt_measures")
    val altMeasures: Any? = null,

    @field:SerializedName("tag_id")
    val tagId: Any? = null,

    @field:SerializedName("nf_p")
    val nfP: Double? = null,

    @field:SerializedName("lat")
    val lat: Int? = null,

    @field:SerializedName("lng")
    val lng: Int? = null,

    @field:SerializedName("consumed_at")
    val consumedAt: String? = null,

    @field:SerializedName("nix_item_name")
    val nixItemName: Any? = null,

    @field:SerializedName("upc")
    val upc: Any? = null,

    @field:SerializedName("photo")
    val photo: Photo? = null,

    @field:SerializedName("brand_name")
    val brandName: Any? = null,

    @field:SerializedName("serving_weight_grams")
    val servingWeightGrams: Double? = null,

    @field:SerializedName("nf_total_carbohydrate")
    val nfTotalCarbohydrate: Double? = null,

    @field:SerializedName("full_nutrients")
    val fullNutrients: List<FullNutrientsItem?>? = null,

    @field:SerializedName("tags")
    val tags: Any? = null,

    @field:SerializedName("nix_brand_name")
    val nixBrandName: Any? = null,

    @field:SerializedName("serving_qty")
    val servingQty: Int? = null,

    @field:SerializedName("nf_calories")
    val nfCalories: Double? = null,

    @field:SerializedName("nf_sodium")
    val nfSodium: Double? = null,

    @field:SerializedName("class_code")
    val classCode: Any? = null,

    @field:SerializedName("nf_dietary_fiber")
    val nfDietaryFiber: Double? = null
)

data class Photo(

    @field:SerializedName("is_user_uploaded")
    val isUserUploaded: Boolean? = null,

    @field:SerializedName("thumb")
    val thumb: String? = null,

    @field:SerializedName("highres")
    val highres: String? = null
)

data class FullNutrientsItem(

    @field:SerializedName("value")
    val value: Double? = null,

    @field:SerializedName("attr_id")
    val attrId: Int? = null
)

data class Metadata(
    val any: Any? = null
)
