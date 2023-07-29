package mdj.app.assessment.models

data class PetResponse(
    val hits: List<PetModel>,
    val total: Int,
    val totalHits: Int
)