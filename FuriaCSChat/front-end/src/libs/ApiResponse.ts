import FuriaErrorCode from "./FuriaErrorCode";

export default interface ApiResponse<T> {
  success: boolean;
  data: T;
  errorCode: FuriaErrorCode;
  errorMessage: string;
}
