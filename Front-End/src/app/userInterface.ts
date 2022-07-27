export interface UserInterface
{
  "id": number,
  "username": string,
  "name": string,
  "surname": string,
  "email": string,
  "appUserRole": string,
  "link_img" : string,
  "enabled": boolean,
  "authorities": [
    {
      "authority": string
    }
  ],
  "credentialsNonExpired": boolean,
  "accountNonExpired": boolean,
  "accountNonLocked": boolean
}
