describe('Loginpage', () => {
  it('should view the profile of the currently logged in user', () => {
    cy.visit('http://localhost:4200');
    cy.get('a[href="/users"]').click()
    cy.url().should('include', '/users');
    cy.get(".card:first").click({force: true})
    cy.url().should('include', '/profile');
    cy.get("#asked-questions").should('contain.text', 'Asked Questions');
    cy.get("#answers-for-questions").should('contain.text', 'Answers For Questions');
    cy.get("#mentioned-tags").should('contain.text', 'Tags Mentioned In Asked Questions');
  });
});
