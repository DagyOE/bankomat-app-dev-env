export class CardVerificationRequest {
    cardNumber?: string;
    expirationDate?: string;
    cvv?: string;
    pin?: string;
    transactionId?: string;
    atmId?: string;
    requestTime?: Date;
}