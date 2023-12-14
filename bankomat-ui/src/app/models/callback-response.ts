import {CardVerificationResponse} from "./card-verification-response";
import {Error} from "./error";
import {WithdrawalResponse} from "./withdrawal-response";

export class CallbackResponse {
  isCompleted?: boolean;
  error?: Error;
  response?: CardVerificationResponse | WithdrawalResponse;
}
