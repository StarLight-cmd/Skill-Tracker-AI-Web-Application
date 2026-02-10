import { describe, test, expect, vi, beforeEach } from "vitest";
import { render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";

/* ---- Mock modules FIRST ---- */
vi.mock("../components/Banner", () => ({
  default: () => <div data-testid="banner">Banner</div>
}));

vi.mock("../components/UserSkills", () => ({
  default: ({ userId }) => (
    <div data-testid="user-skills">UserSkills for {userId}</div>
  )
}));

// Prevent any axios/fetch calls from ChatBot or other modules
vi.mock("../components/ChatBot", () => ({
  default: () => <div data-testid="chatbot">ChatBot</div>
}));

// If you have a shared axios instance like src/api/axios.js, mock it too
vi.mock("../api/axios", () => ({
  default: {
    get: vi.fn(),
    post: vi.fn(),
    put: vi.fn(),
    delete: vi.fn(),
  }
}));

/* ---- NOW import Home ---- */
import Home from "../components/Home";

/* ---- Mock localStorage ---- */
beforeEach(() => {
  Storage.prototype.getItem = vi.fn(() =>
    JSON.stringify({ id: 42, name: "Sashen" })
  );
});

describe("Home Component", () => {
  test("renders dashboard title", () => {
    render(
      <MemoryRouter>
        <Home />
      </MemoryRouter>
    );
    expect(screen.getByText("SkillTrack Dashboard")).toBeInTheDocument();
  });

  test("passes userId to UserSkills", () => {
    render(
      <MemoryRouter>
        <Home />
      </MemoryRouter>
    );
    expect(screen.getByTestId("user-skills")).toHaveTextContent("42");
  });
});
