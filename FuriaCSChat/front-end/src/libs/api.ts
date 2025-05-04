import axios from "axios";
import ApiResponse from "./ApiResponse";
import UserLoginDTO from "./users/UserLoginDTO";
import UserDetailedView from "./users/UserDetailedView";
import VotifyErrorCode from "./FuriaErrorCode";
import UserSimpleView from "./users/UserSimpleView";
import UserRegisterDTO from "./users/UserRegisterDTO";
import MessageSendDTO from "./chat/MessageSendDTO";
import MessageView from "./chat/MessageView";

export const api = axios.create({
  baseURL: "http://" + process.env.NEXT_PUBLIC_API_BASE_URL + "/api",
  withCredentials: true,
});

export const getUserById = async (
  id: number,
): Promise<ApiResponse<UserSimpleView | null>> => {
  return await commonRequester(async () => {
    const { data } = await api.get<ApiResponse<UserSimpleView>>(`/users/${id}`);
    return data;
  });
};

export const getCurrentUser = async (): Promise<
  ApiResponse<UserDetailedView | null>
> => {
  return await commonRequester(async () => {
    const { data } = await api.get<ApiResponse<UserDetailedView>>("/users/me");
    return data;
  });
};

export const logout = async (): Promise<ApiResponse<null>> => {
  return await commonRequester(async () => {
    const { data } = await api.post<ApiResponse<null>>("/auth/logout");
    return data;
  });
};

export const login = async (
  credentials: UserLoginDTO,
): Promise<ApiResponse<null>> => {
  return await commonRequester(async () => {
    const { data } = await api.post<ApiResponse<null>>(
      "/auth/login",
      credentials,
    );
    return data;
  });
};

export const register = async (
  form: UserRegisterDTO,
): Promise<ApiResponse<UserDetailedView>> => {
  return await commonRequester(async () => {
    const { data } = await api.post<ApiResponse<UserDetailedView>>(
      "/auth/register",
      form,
    );
    return data;
  });
};

export const sendMessage = async (
  form: MessageSendDTO,
): Promise<ApiResponse<MessageView>> => {
  return await commonRequester(async () => {
    const { data } = await api.post<ApiResponse<MessageView>>("/chat", form);
    return data;
  });
};

export const getAllMessages = async (): Promise<ApiResponse<MessageView[]>> => {
  return await commonRequester(async () => {
    const { data } = await api.get<ApiResponse<MessageView>>("/chat");
    return data;
  });
};

const commonRequester = async <T>(
  request: () => Promise<ApiResponse<T | null>>,
) => {
  try {
    return await request();
  } catch (error: any) {
    if (error.response === undefined) return getBackupErrorObject();
    if (
      error.response.data.errorCode === VotifyErrorCode.ACCESS_TOKEN_EXPIRED ||
      error.response.data.errorCode === VotifyErrorCode.COMMON_UNAUTHORIZED
    ) {
      return await refreshAndDoRequestAgain(request);
    }
    return error.response.data;
  }
};

const refreshAndDoRequestAgain = async <T>(
  request: () => Promise<ApiResponse<T | null>>,
) => {
  try {
    await api.post<ApiResponse<null>>("/auth/refresh-tokens");
    return await request();
  } catch (error: any) {
    if (error.response === undefined) return getBackupErrorObject();
    return error.response.data;
  }
};

const getBackupErrorObject = (): ApiResponse<null> => ({
  success: false,
  data: null,
  errorCode: VotifyErrorCode.CLIENT_CONNECTION_FAILED,
  errorMessage: "It was not possible to make a request to the API.",
});
