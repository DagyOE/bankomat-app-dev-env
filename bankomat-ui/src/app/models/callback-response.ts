import {CardVerificationResponse} from "./card-verification-response";
import {Error} from "./error";
import {WithdrawalResponse} from "./withdrawal-response";
import {CardEjectResponse} from "./card-eject-response";

type responseType = CardVerificationResponse | WithdrawalResponse | CardEjectResponse;

export class CallbackResponse {
  isCompleted?: boolean;
  error?: Error;
  response?: responseType;
}
